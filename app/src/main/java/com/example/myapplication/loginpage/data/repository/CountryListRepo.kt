package com.example.myapplication.loginpage.data.repository

import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.loginpage.api.CountryRetrofitInstance
import com.example.myapplication.loginpage.data.models.CountryListDataModel
import com.example.myapplication.loginpage.domain.irepository.ICountryListRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CountryListRepo: ICountryListRepo {
    override suspend fun getCountryListData(): Flow<LibraryResultEvent<List<CountryListDataModel>>> = flow{
        emit(LibraryResultEvent.OnLoading)

        val apiService = CountryRetrofitInstance.create("https://www.jsonkeeper.com/b/")

        try {
            val response = apiService.getCountryList("IU1K")
            if (response.isSuccessful) {
                val countryList = response.body() ?: emptyList()
                emit(LibraryResultEvent.OnSuccess(countryList))
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