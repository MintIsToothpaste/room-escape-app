package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class RecommendedTheme(val themeName: String, val cafeName: String, val url: String)
class RecommendedThemeViewModel : ViewModel(){
    val itemsListData = MutableLiveData<ArrayList<RecommendedTheme>>()
    val items = ArrayList<RecommendedTheme>()

    init {
        items.add(RecommendedTheme("Odd bar", "씨이스케이프", "oddbar.jpg"))
        items.add(RecommendedTheme("화생설화", "비트포비아", "tale.jpg"))
        items.add(RecommendedTheme("머니머니패키지", "키이스케이프", "moneypackage.jpg"))
        items.add(RecommendedTheme("퀘스천마크", "퀘스천마크", "question.jpg"))
        items.add(RecommendedTheme("fl[ae]sh", "황금열쇠 건대점", "fl[ae]sh.png"))
        items.add(RecommendedTheme("FILM BY EDDY", "키이스케이프", "eddy.jpg"))
    }

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: RecommendedTheme) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }
    fun updateItem(pos: Int, item: RecommendedTheme) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }
    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

}