package com.example.myapplication.booklistpage.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.R
import com.example.myapplication.booklistpage.data.repository.BookListRepo
import com.example.myapplication.booklistpage.domain.usecases.BookListUseCase
import com.example.myapplication.booklistpage.presentation.viewmodels.BookListViewModel
import com.example.myapplication.booklistpage.presentation.viewmodels.BookListViewModelFactory
import com.example.myapplication.databinding.FragmentLibraryBinding


class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding

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
    }

    private fun loadData() {
        viewModel.getBookListData()
    }

    private fun observer() {
        viewModel.bookListData.observe(viewLifecycleOwner){result ->
            when(result){
                is LibraryResultEvent.OnSuccess ->{
                    val bookList = result.data
                }
                is LibraryResultEvent.OnFailure ->{

                }
                is LibraryResultEvent.OnLoading -> {

                }
            }
        }
    }


}