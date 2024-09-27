package com.example.myapplication.booklistpage.domain.usecases

import com.example.myapplication.booklistpage.domain.irepository.IBookListRepo

class BookListUseCase(private val repo: IBookListRepo) {
    suspend operator fun invoke() = repo.getBookListData()
}