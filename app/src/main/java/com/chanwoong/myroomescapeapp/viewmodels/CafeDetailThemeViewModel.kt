package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class CafeDetailThemeList(val name: String, // 테마명
                     val cafeName: String, // 카페명
                     val url: String, // 이미지
                     val location: String) // 위치

class CafeDetailThemeViewModel : ViewModel(){
    val itemsListData = MutableLiveData<ArrayList<CafeDetailThemeList>>()
    val items = ArrayList<CafeDetailThemeList>()


    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: CafeDetailThemeList) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }
    fun updateItem(pos: Int, item: CafeDetailThemeList) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }
    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

}