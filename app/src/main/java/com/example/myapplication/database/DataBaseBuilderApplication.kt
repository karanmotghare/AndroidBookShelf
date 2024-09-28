package com.example.myapplication.database

import android.app.Application
import androidx.room.Room
import com.example.myapplication.database.db.BookDataBase

class DataBaseBuilderApplication: Application() {
    companion object{
        lateinit var bookDataBase: BookDataBase
    }

    override fun onCreate() {
        super.onCreate()
        bookDataBase = Room.databaseBuilder(
            applicationContext,
            BookDataBase::class.java,
            BookDataBase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}