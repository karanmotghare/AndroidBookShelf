package com.example.myapplication.loginpage.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryRetrofitInstance {

    fun create(baseUrl: String): CountryListApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryListApiService::class.java)
    }
}