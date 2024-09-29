package com.example.myapplication.loginpage.domain.usecases

import com.example.myapplication.loginpage.domain.irepository.IUserIpLocationRepo

class UserIpLocationUseCase(private val repo: IUserIpLocationRepo) {
    suspend operator fun invoke() = repo.getUserIpLocation()
}