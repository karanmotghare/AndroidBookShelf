package com.example.myapplication.booklistpage.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.booklistpage.domain.usecases.BookListUseCase

class BookListViewModelFactory(private val useCase: BookListUseCase): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        return BookListViewModel(useCase) as T
    }
}