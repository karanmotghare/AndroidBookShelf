package com.example.myapplication.loginpage.data.models

import com.google.gson.annotations.SerializedName

data class CountryListDataModel (

    @SerializedName("country")
    val country : String,

    @SerializedName("region")
    val region : String
)