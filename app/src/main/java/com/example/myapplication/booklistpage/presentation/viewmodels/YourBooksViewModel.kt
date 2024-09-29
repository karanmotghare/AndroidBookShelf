package com.example.myapplication.booklistpage.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import com.example.myapplication.database.DataBaseBuilderApplication
import com.example.myapplication.database.entitiy.BookEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YourBooksViewModel(): ViewModel() {

    val bookDao = DataBaseBuilderApplication.bookDataBase.getBookDao()
    val bookList: LiveData<List<BookEntity>> = bookDao.getAllBook()

    fun addBook(bookListDataModel: BooklistDataModel){
        viewModelScope.launch(Dispatchers.IO) {
            bookDao.addBook(convertToEntity(bookListDataModel))
        }
    }

    fun delete(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            bookDao.deleteBook(id)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            bookDao.deleteAllBook()
        }
    }

    private fun convertToEntity(bookListDataModel: BooklistDataModel): BookEntity{
        return BookEntity(
            id = bookListDataModel.id,
            img = bookListDataModel.img,
            score = bookListDataModel.score,
            popularity = bookListDataModel.popularity,
            title = bookListDataModel.title,
            publishedChapterDate = bookListDataModel.publishedChapterDate,
            isWishListed = bookListDataModel.isWishListed
        )
    }

}