package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chanwoong.myroomescapeapp.model.RoomsModel

data class ListLayout(val name: String, // 장소명
                 val road: String, // 도로명 주소
                 val address: String, // 지번 주소
                 val x: Double, // 경도(Longitude)
                 val y: Double) // 위도(Latitude)

class CafeViewModel : ViewModel(){
    val itemsListData = MutableLiveData<ArrayList<ListLayout>>()
    val items = ArrayList<ListLayout>()

    /*init {
        items.add(ListLayout("키이스케이프 강남점", "1111", "1111", 11.11, 11.11))
        items.add(ListLayout("키이스케이프 홍대점", "1111", "1111", 11.11, 11.11))
        items.add(ListLayout("키이스케이프 쌍문점", "1111", "1111", 11.11, 11.11))
    }*/

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: ListLayout) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }
    fun updateItem(pos: Int, item: ListLayout) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }
    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

}