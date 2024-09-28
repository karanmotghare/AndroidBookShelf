package com.example.myapplication.booklistpage.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import com.example.myapplication.booklistpage.presentation.adapters.LibraryBookItemAdapter
import com.example.myapplication.booklistpage.presentation.viewmodels.YourBooksViewModel
import com.example.myapplication.booklistpage.presentation.viewmodels.YourBooksViewModelFactory
import com.example.myapplication.database.entitiy.BookEntity
import com.example.myapplication.databinding.FragmentYourBookBinding

class YourBookFragment : Fragment() {
    private lateinit var binding: FragmentYourBookBinding
    private lateinit var yourBooksViewModel: YourBooksViewModel
    private lateinit var libraryBookItemAdapter: LibraryBookItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = YourBooksViewModelFactory()
        yourBooksViewModel = ViewModelProvider(this, factory).get(YourBooksViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_your_book, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        yourBooksViewModel.bookList.observe(viewLifecycleOwner, { book->
            Log.d("yourbookfag",book.toString())
            Ui(convertToBookList(book))
        })

    }

    private fun Ui(bookList: List<BooklistDataModel>) {
        libraryBookItemAdapter = LibraryBookItemAdapter(bookList){book ->
            yourBooksViewModel.delete(book.id)
        }
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bookRecyclerView.adapter = libraryBookItemAdapter
    }

    private fun convertToBookList(bookEntities: List<BookEntity>): List<BooklistDataModel>{
        return bookEntities.map{ convertToBook(it) }
    }

    private fun convertToBook(bookEntity: BookEntity): BooklistDataModel{
        return BooklistDataModel(
            id = bookEntity.id,
            img = bookEntity.img,
            score = bookEntity.score,
            popularity = bookEntity.popularity,
            title = bookEntity.title,
            publishedChapterDate = bookEntity.publishedChapterDate,
            isWishListed = bookEntity.isWishListed
        )
    }
}