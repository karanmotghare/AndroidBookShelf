package com.example.myapplication.loginpage.api

import com.example.myapplication.loginpage.data.models.CountryListDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CountryListApiService {
    @GET
    suspend fun getCountryList(@Url endpoint: String): Response<List<CountryListDataModel>>
}