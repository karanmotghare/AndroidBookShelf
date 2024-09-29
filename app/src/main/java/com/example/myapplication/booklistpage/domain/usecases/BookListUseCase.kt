package com.example.myapplication.booklistpage.domain.usecases

import com.example.myapplication.booklistpage.domain.irepository.IBookListRepo

// usecase because separation between the business logic and user interface
class BookListUseCase(private val repo: IBookListRepo) {
    suspend operator fun invoke() = repo.getBookListData()
}