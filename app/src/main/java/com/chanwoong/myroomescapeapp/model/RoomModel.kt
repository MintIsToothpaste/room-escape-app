package com.chanwoong.myroomescapeapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RoomsModel(
    @SerializedName("name")
    val title: String,
    @SerializedName("x")
    val lng: Double,
    @SerializedName("y")
    val lat: Double
) : Serializable