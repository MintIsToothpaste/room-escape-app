package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class ThemeList(val name: String, // 테마명
                     val cafeName: String, // 카페명
                    val url: String, // 이미지
                    val location: String) // 위치

class ThemeViewModel : ViewModel(){
    private val itemsListData = MutableLiveData<ArrayList<ThemeList>>()
    val items = ArrayList<ThemeList>()

    private val selectItems = ArrayList<ThemeList>()

    private var selectCafe: String? = null

    init {
        items.add(ThemeList("Odd bar", "씨이스케이프", "oddbar.jpg", "경기"))
        items.add(ThemeList("화생설화", "비트포비아 던전101", "tale.jpg", "서울"))
        items.add(ThemeList("MST 엔터테인먼트", "비트포비아 던전101", "mst.png", "서울"))
        items.add(ThemeList("머니머니패키지", "키이스케이프 LOG_IN 1", "moneypackage.jpg", "서울"))
        items.add(ThemeList("퀘스천마크", "퀘스천마크", "question.jpg", "서울"))
        items.add(ThemeList("fl[ae]sh", "황금열쇠 유토피아호", "fl[ae]sh.png", "서울"))
        items.add(ThemeList("FILM BY EDDY", "키이스케이프", "eddy.jpg", "서울"))
        items.add(ThemeList("거상", "싸인이스케이프 홍대점", "merchant.jpg", "서울"))
        items.add(ThemeList("그림자 없는 상자", "단편선", "shadow.png", "서울"))
        items.add(ThemeList("NERD", "키이스케이프 강남더오름", "nerd.jpg", "서울"))
        items.add(ThemeList("세렌디피티", "넥스트에디션 건대 보네르관", "serendipity.png", "서울"))
        items.add(ThemeList("이세계 용사", "이스케이프랩", "warrior.jpg", "서울"))
        items.add(ThemeList("NOMON", "황금열쇠 강남점", "nomon.jpg", "서울"))

    }

    fun getSelectItem(pos: Int) = selectItems[pos]

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val selectItemsSize
        get() = selectItems.size

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

    fun addSelectItem(item: ThemeList) {
        selectItems.add(item)
        itemsListData.value = selectItems // let the observer know the livedata changed
    }

    fun setCafe(cafe: String?) {
        selectCafe = cafe
    }

    fun getCafe(): String?{
        return selectCafe
    }

}