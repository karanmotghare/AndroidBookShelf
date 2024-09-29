package com.example.myapplication.loginpage.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.loginpage.data.models.UserIpLocationDataModel
import com.example.myapplication.loginpage.domain.usecases.UserIpLocationUseCase
import kotlinx.coroutines.launch

class UserIpLocationViewModel(private val userIpLocationUseCase: UserIpLocationUseCase) : ViewModel() {
    private val _ipLocationData: MutableLiveData<LibraryResultEvent<UserIpLocationDataModel>> = MutableLiveData()
    val ipLocationData: LiveData<LibraryResultEvent<UserIpLocationDataModel>>
        get() = _ipLocationData

    fun getIpLocationData() = viewModelScope.launch {
        userIpLocationUseCase.invoke().collect{
            _ipLocationData.postValue(it)
        }
    }
}