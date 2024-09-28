package com.example.myapplication.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity (
    @PrimaryKey
    var id : String,
    var img : String,
    val score :  Double,
    val popularity :  Long,
    val title : String,
    val publishedChapterDate : Long
)