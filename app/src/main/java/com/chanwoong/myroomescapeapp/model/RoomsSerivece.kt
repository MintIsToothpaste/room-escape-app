package com.chanwoong.myroomescapeapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RoomsService {
    @GET("/v1/search/local.json")
    fun getRoomsList(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") secretKey: String,
        @Query("query") query: String,
        @Query("displayCount") displayCount:Int = 20
    ): Call<RoomsResponse>
}