package com.example.myapplication.booklistpage.data.models

import com.google.gson.annotations.SerializedName

data class BooklistDataModel(

    @SerializedName("id")
    val id : String,

    @SerializedName("image")
    val img : String,

    @SerializedName("score")
    val score :  Double,

    @SerializedName("popularity")
    val popularity :  Long,

    @SerializedName("title")
    val title : String,

    @SerializedName("publishedChapterDate")
    val publishedChapterDate : Long
)
