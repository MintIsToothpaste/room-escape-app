package com.chanwoong.myroomescapeapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RoomsModel(
    @SerializedName("name")
    val title: String,
    @SerializedName("mapx")
    val lng: String,
    @SerializedName("mapy")
    val lat: String
) : Serializable