package com.example.myapplication.booklistpage.data.repository

import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.booklistpage.api.RetrofitInstance
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import com.example.myapplication.booklistpage.domain.irepository.IBookListRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class BookListRepo: IBookListRepo {
    override suspend fun getBookListData(): Flow<LibraryResultEvent<List<BooklistDataModel>>> = flow {
        emit(LibraryResultEvent.OnLoading)

        val apiService = RetrofitInstance.create("https://www.jsonkeeper.com/b/")

        try {
//            val response = RetrofitInstance.api.getBookList()
            val response = apiService.getBookList("CNGI")
            if (response.isSuccessful) {
                val bookList = response.body() ?: emptyList()
                emit(LibraryResultEvent.OnSuccess(bookList))
            } else {
                emit(LibraryResultEvent.OnFailure("Api Error: ${response.code()}"))
            }
        }catch (e : Exception){
            emit(LibraryResultEvent.OnFailure("Unknown error : ${e.message}"))
        }catch (e : IOException){
            emit(LibraryResultEvent.OnFailure("Unknown error : ${e.message}"))
        }

    }
}