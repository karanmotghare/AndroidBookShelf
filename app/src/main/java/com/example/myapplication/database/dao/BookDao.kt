package com.example.myapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entitiy.BookEntity

@Dao
interface BookDao {

    @Query("SELECT * FROM BookEntity")
    fun getAllBook() : LiveData<List<BookEntity>>

    @Insert
    fun addBook(book: BookEntity)

    @Query("DELETE FROM BookEntity where id = :id")
    fun deleteBook(id: String)

    @Query("DELETE FROM BookEntity")
    suspend fun deleteAllBook()

}