package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class MyPost(val title: String, val post: String, val nickname: String)

class MyPostViewModel : ViewModel(){
    private val itemsListData = MutableLiveData<ArrayList<MyPost>>()
    private val items = ArrayList<MyPost>()

    init {
        items.add(MyPost("공지사항", "아직 개발중 입니다", " 관리자"))
    }

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: MyPost) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }

    fun updateItem(pos: Int, item: MyPost) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }

    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

}