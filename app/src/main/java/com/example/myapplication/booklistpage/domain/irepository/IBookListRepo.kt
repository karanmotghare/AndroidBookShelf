package com.example.myapplication.booklistpage.domain.irepository

import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import kotlinx.coroutines.flow.Flow

interface IBookListRepo {
    suspend fun getBookListData(): Flow<LibraryResultEvent<List<BooklistDataModel>>>
}