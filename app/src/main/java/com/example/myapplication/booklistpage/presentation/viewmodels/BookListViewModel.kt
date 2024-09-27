package com.example.myapplication.booklistpage.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import com.example.myapplication.booklistpage.domain.usecases.BookListUseCase
import kotlinx.coroutines.launch

class BookListViewModel( private val bookListUseCase: BookListUseCase
) : ViewModel() {
    private val _bookListData: MutableLiveData<LibraryResultEvent<List<BooklistDataModel>>> = MutableLiveData()
    val bookListData: LiveData<LibraryResultEvent<List<BooklistDataModel>>>
        get() = _bookListData

    fun getBookListData() = viewModelScope.launch {
        bookListUseCase.invoke().collect{
            _bookListData.postValue(it)
        }
    }

}