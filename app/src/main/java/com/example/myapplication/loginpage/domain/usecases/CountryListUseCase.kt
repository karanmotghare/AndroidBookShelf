package com.example.myapplication.loginpage.domain.usecases

import com.example.myapplication.loginpage.domain.irepository.ICountryListRepo

class CountryListUseCase(private val repo: ICountryListRepo) {
    suspend operator fun invoke() = repo.getCountryListData()
}