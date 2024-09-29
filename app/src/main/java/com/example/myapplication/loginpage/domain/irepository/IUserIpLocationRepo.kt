package com.example.myapplication.loginpage.domain.irepository

import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.loginpage.data.models.UserIpLocationDataModel
import kotlinx.coroutines.flow.Flow

interface IUserIpLocationRepo {
    suspend fun getUserIpLocation(): Flow<LibraryResultEvent<UserIpLocationDataModel>>
}