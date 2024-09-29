package com.example.myapplication.loginpage.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserIpLocationRetrofitInstance {

    fun create(baseUrl: String): UserIpLocationApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserIpLocationApiService::class.java)
    }
}