package com.chanwoong.myroomescapeapp.model

import com.google.gson.annotations.SerializedName

data class RoomsResponse(
    @SerializedName("rss")
    var roomsChannel: RoomsChannel
)
data class RoomsChannel(
    @SerializedName("channel")
    var item : RoomsList
)
data class RoomsList(
    // 이렇게 마지막 list에 담긴 아이템들은 RoomsModel 타입의 리스트로 저장한다.
    @SerializedName("item")
    val items: List<RoomsModel> = arrayListOf<RoomsModel>()
)
