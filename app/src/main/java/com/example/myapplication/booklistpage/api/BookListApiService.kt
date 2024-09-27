package com.example.myapplication.booklistpage.api

import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import retrofit2.Response
import retrofit2.http.GET

interface BookListApiService {
    @GET("CNGI")
    suspend fun getBookList(): Response<List<BooklistDataModel>>
}