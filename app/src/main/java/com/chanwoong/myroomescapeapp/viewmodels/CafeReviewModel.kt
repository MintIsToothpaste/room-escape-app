package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class CafeReviewList(
    val nickname: String, // 닉네임
    val time: String, // 시간
    val rating: String, // 별점
    val review: String, // 리뷰
    val cafe: String) // 리뷰한 카페

class CafeReviewModel : ViewModel(){
    val itemsListData = MutableLiveData<ArrayList<CafeReviewList>>()
    val items = ArrayList<CafeReviewList>()

    private val selectItems = ArrayList<CafeReviewList>()

    fun clearItem(){
        selectItems.clear()
    }

    fun getSelectItem(pos: Int) = selectItems[pos]

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val selectItemsSize
        get() = selectItems.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: CafeReviewList) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }

    fun updateItem(pos: Int, item: CafeReviewList) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }

    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

    fun addSelectItem(item: CafeReviewList) {
        selectItems.add(item)
        itemsListData.value = selectItems // let the observer know the livedata changed
    }

}