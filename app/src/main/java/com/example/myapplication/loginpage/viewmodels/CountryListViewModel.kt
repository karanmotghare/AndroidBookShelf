package com.example.myapplication.loginpage.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.loginpage.data.models.CountryListDataModel
import com.example.myapplication.loginpage.domain.usecases.CountryListUseCase
import kotlinx.coroutines.launch

class CountryListViewModel(private val countryListUseCase: CountryListUseCase): ViewModel() {
    private val _countryListData: MutableLiveData<LibraryResultEvent<List<CountryListDataModel>>> = MutableLiveData()
    val countryListData: LiveData<LibraryResultEvent<List<CountryListDataModel>>>
        get() = _countryListData

    fun getCountryListData() = viewModelScope.launch {
        countryListUseCase.invoke().collect{
            _countryListData.postValue(it)
        }
    }
}