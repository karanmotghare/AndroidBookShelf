package com.example.myapplication.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.BookDao
import com.example.myapplication.database.entitiy.BookEntity

@Database(entities = [BookEntity::class], version = 2)
abstract class BookDataBase : RoomDatabase() {

    companion object{
        const val NAME = "Book_DataBase"
    }

    abstract fun getBookDao() : BookDao
}