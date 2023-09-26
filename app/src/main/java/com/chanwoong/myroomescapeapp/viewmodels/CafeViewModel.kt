package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chanwoong.myroomescapeapp.model.RoomsModel

data class CafeList(val name: String, // 장소명
                 val x: Double, // 경도(Longitude)
                 val y: Double, // 위도(Latitude)
                 val url: String, // 이미지
                 val location: String) // 위치

class CafeViewModel : ViewModel(){
    val itemsListData = MutableLiveData<ArrayList<CafeList>>()
    val items = ArrayList<CafeList>()

    init {
        items.add(CafeList("키이스케이프 LOG_IN 1", 11.11, 11.11, "keyescape.png", "서울"))
        items.add(CafeList("키이스케이프 LOG_IN 2", 11.11, 11.11, "keyescape.png", "서울"))
        items.add(CafeList("키이스케이프 메모리컴퍼니", 11.11, 11.11, "keyescape.png", "서울"))
        items.add(CafeList("키이스케이프 우주라이크", 11.11, 11.11, "keyescape.png", "서울"))
        items.add(CafeList("키이스케이프 강남더오름", 11.11, 11.11, "keyescape.png", "서울"))
        items.add(CafeList("키이스케이프 홍대", 11.11, 11.11, "keyescape.png", "서울"))
        items.add(CafeList("키이스케이프 강남", 11.11, 11.11, "keyescape.png", "서울"))
    }

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: CafeList) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }
    fun updateItem(pos: Int, item: CafeList) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }
    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

}