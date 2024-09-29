package com.example.myapplication.booklistpage.api

import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface BookListApiService {
//    @GET("CNGI")
//    suspend fun getBookList(): Response<List<BooklistDataModel>>

    @GET
    suspend fun getBookList(@Url endpoint: String): Response<List<BooklistDataModel>>
}