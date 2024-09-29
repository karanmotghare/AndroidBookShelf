package com.example.myapplication.loginpage.domain.irepository

import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.loginpage.data.models.CountryListDataModel
import kotlinx.coroutines.flow.Flow

interface ICountryListRepo {
    suspend fun getCountryListData(): Flow<LibraryResultEvent<List<CountryListDataModel>>>
}