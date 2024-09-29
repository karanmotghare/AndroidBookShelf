package com.example.myapplication.loginpage.data.repository

import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.loginpage.api.UserIpLocationRetrofitInstance
import com.example.myapplication.loginpage.data.models.UserIpLocationDataModel
import com.example.myapplication.loginpage.domain.irepository.IUserIpLocationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class UserIpLocationRepo : IUserIpLocationRepo{
    override suspend fun getUserIpLocation(): Flow<LibraryResultEvent<UserIpLocationDataModel>> = flow{
        emit(LibraryResultEvent.OnLoading)

        val apiService = UserIpLocationRetrofitInstance.create("http://ip-api.com/")

        try{
            val response = apiService.getUserIpLocation("json")
            if(response.isSuccessful){
                val countryList = response.body()
                if(countryList != null){
                    emit(LibraryResultEvent.OnSuccess(countryList))
                }else{
                    emit(LibraryResultEvent.OnFailure("No data: ${response.code()}"))
                }
            }else {
                emit(LibraryResultEvent.OnFailure("Api Error: ${response.code()}"))
            }
        }catch (e : Exception){
            emit(LibraryResultEvent.OnFailure("Unknown error : ${e.message}"))
        }catch (e : IOException){
            emit(LibraryResultEvent.OnFailure("Unknown error : ${e.message}"))
        }
    }
}