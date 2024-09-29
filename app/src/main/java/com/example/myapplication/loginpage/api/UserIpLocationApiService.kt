package com.example.myapplication.loginpage.api

import com.example.myapplication.loginpage.data.models.UserIpLocationDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface UserIpLocationApiService {
    @GET
    suspend fun getUserIpLocation(@Url endpoint: String): Response<UserIpLocationDataModel>
}