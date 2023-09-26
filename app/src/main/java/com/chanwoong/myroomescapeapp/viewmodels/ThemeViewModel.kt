package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class ThemeList(val name: String, // 테마명
                     val cafeName: String, // 카페명
                    val url: String, // 이미지
                    val location: String) // 위치

class ThemeViewModel : ViewModel(){
    val itemsListData = MutableLiveData<ArrayList<ThemeList>>()
    val items = ArrayList<ThemeList>()

    init {
        items.add(ThemeList("Odd bar", "씨이스케이프", "oddbar.jpg", "경기"))
        items.add(ThemeList("화생설화", "비트포비아", "tale.jpg", "서울"))
        items.add(ThemeList("머니머니패키지", "키이스케이프", "moneypackage.jpg", "서울"))
        items.add(ThemeList("퀘스천마크", "퀘스천마크", "question.jpg", "서울"))
        items.add(ThemeList("fl[ae]sh", "황금열쇠 건대점", "fl[ae]sh.png", "서울"))
        items.add(ThemeList("FILM BY EDDY", "키이스케이프", "eddy.jpg", "서울"))
    }

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: ThemeList) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }
    fun updateItem(pos: Int, item: ThemeList) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }
    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

}