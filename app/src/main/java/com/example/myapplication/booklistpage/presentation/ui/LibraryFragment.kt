package com.example.myapplication.booklistpage.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.R
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import com.example.myapplication.booklistpage.data.repository.BookListRepo
import com.example.myapplication.booklistpage.domain.usecases.BookListUseCase
import com.example.myapplication.booklistpage.presentation.adapters.LibraryBookItemAdapter
import com.example.myapplication.booklistpage.presentation.viewmodels.BookListViewModel
import com.example.myapplication.booklistpage.presentation.viewmodels.BookListViewModelFactory
import com.example.myapplication.booklistpage.presentation.viewmodels.YourBooksViewModel
import com.example.myapplication.booklistpage.presentation.viewmodels.YourBooksViewModelFactory
import com.example.myapplication.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayout

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private val bookList = mutableListOf<BooklistDataModel>()
    private lateinit var libraryBookItemAdapter: LibraryBookItemAdapter
    private val bookByYear = mutableMapOf<Int, List<BooklistDataModel>>() // Year to anime list mapping
    private val years = mutableListOf<Int>()
    private var isTabSelectedScroll = false
    private lateinit var yourBooksViewModel: YourBooksViewModel

    private val viewModelFactory by lazy {
        BookListViewModelFactory(
            BookListUseCase(
                BookListRepo()
            )
        )
    }

    private val viewModel: BookListViewModel by lazy {
        ViewModelProvider(viewModelStore, viewModelFactory).get(BookListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = YourBooksViewModelFactory()
        yourBooksViewModel = ViewModelProvider(this, factory).get(YourBooksViewModel::class.java)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_library, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        ui()
    }

    private fun loadData() {
        viewModel.getBookListData()
    }

    private fun observer() {
        viewModel.bookListData.observe(viewLifecycleOwner){result ->
            when(result){
                is LibraryResultEvent.OnSuccess ->{
                    val bookItem = result.data
                    bookList.clear()
                    bookList.addAll(bookItem)
                    bookList.sortByDescending { it.publishedChapterDate }
                    yourBooksViewModel.bookList.observe(viewLifecycleOwner){wishlistbook ->

                        val wishlistedbookids = wishlistbook.map { it.id }
                        bookList.forEach{ book ->
                            book.isWishListed = wishlistedbookids.contains(book.id)
                        }
                        libraryBookItemAdapter.notifyDataSetChanged()
                        setupTabsAndYearData()
                    }
                }
                is LibraryResultEvent.OnFailure ->{

                }
                is LibraryResultEvent.OnLoading -> {

                }
            }
        }
    }

    private fun ui() {

        libraryBookItemAdapter = LibraryBookItemAdapter(bookList){book ->
            val position = bookList.indexOf(book)
            if(position != -1) {
                if (book.isWishListed) {
                    book.isWishListed = !book.isWishListed
                    yourBooksViewModel.delete(book.id)
                } else {
                    book.isWishListed = !book.isWishListed
                    yourBooksViewModel.addBook(book)
                }
                libraryBookItemAdapter.notifyItemChanged(position)
            }
        }
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bookRecyclerView.adapter = libraryBookItemAdapter

        binding.bookRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!isTabSelectedScroll) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
                    val year = getYearFromPosition(firstVisiblePosition)

                    val tabIndex = years.indexOf(year)
                    if (tabIndex != binding.tabLayoutYears.selectedTabPosition) {
                        binding.tabLayoutYears.getTabAt(tabIndex)?.select()
                    }
                }
            }
        })

        binding.tabLayoutYears.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    isTabSelectedScroll = true
                    val selectedYear = years[tab!!.position]
                    val position = getPositionForYears(selectedYear)

                    (binding.bookRecyclerView.layoutManager as? LinearLayoutManager)?.let {
                        val smoothScroller = SmoothScrollerWithOffset(binding.bookRecyclerView.context, 0)
                        smoothScroller.targetPosition = position
                        it.startSmoothScroll(smoothScroller)
                    }

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.bookRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    isTabSelectedScroll = false
                }
            }
        })

    }

    private fun setupTabsAndYearData(){
        bookByYear.clear()
        years.clear()

        bookList.forEach{ book ->
            val year = getYear(book.publishedChapterDate)
            if(bookByYear[year] == null){
                bookByYear[year] = mutableListOf()
                years.add(year)
            }
            bookByYear[year]?.let {
                (it as MutableList).add(book)
            }
        }

        years.sortDescending()
        years.forEach{ year->
            binding.tabLayoutYears.addTab(binding.tabLayoutYears.newTab().setText(year.toString()))
        }

    }

    private fun getYear(data: Long): Int{
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = data*1000
        return calendar.get(java.util.Calendar.YEAR)
    }

    private fun getYearFromPosition(position: Int): Int{
        var accumulatedCount = 0
        for(year in years){
            accumulatedCount += bookByYear[year]?.size ?: 0
            if(position < accumulatedCount){
                return year
            }
        }
        return years.first()
    }

    private fun getPositionForYears(year: Int): Int{
        var position = 0
        for (y in years) {
            if (y == year) break
            position += bookByYear[y]?.size ?: 0
        }
        return position
    }

}

class SmoothScrollerWithOffset(context: Context, private val offset: Int) : LinearSmoothScroller(context) {
    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun calculateDyToMakeVisible(view: View, snapPreference: Int): Int {
        return super.calculateDyToMakeVisible(view, snapPreference) - offset
    }
}