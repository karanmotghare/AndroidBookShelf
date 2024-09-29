package com.example.myapplication.loginpage.data.models

import com.google.gson.annotations.SerializedName

data class UserIpLocationDataModel (

    @SerializedName("status")
    val status : String,

    @SerializedName("country")
    val country : String,

    @SerializedName("countryCode")
    val countryCode : String,

    @SerializedName("region")
    val region : String,

    @SerializedName("regionName")
    val regionName : String,

    @SerializedName("query")
    val query : String
)
