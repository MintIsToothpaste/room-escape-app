package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chanwoong.myroomescapeapp.model.RoomsModel


class CafeViewModel : ViewModel(){
    val itemsListData = MutableLiveData<ArrayList<RoomsModel>>()
    val items = ArrayList<RoomsModel>()

    init {
        items.add(RoomsModel("키이스케이프 강남점", 1111.1, 1111.1))
        items.add(RoomsModel("키이스케이프 홍대점", 1111.1, 1111.1))
        items.add(RoomsModel("키이스케이프 쌍문점", 1111.1, 1111.1))
    }

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: RoomsModel) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }
    fun updateItem(pos: Int, item: RoomsModel) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }
    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

}