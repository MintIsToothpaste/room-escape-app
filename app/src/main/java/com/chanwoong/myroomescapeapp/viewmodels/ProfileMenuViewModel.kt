package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chanwoong.myroomescapeapp.model.RoomsModel

data class Menu(val title: String)

class ProfileMenuViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<Menu>>()
    val items = ArrayList<Menu>()

    init {
        items.add(Menu("내 게시글 관리"))
        items.add(Menu("내 리뷰"))
        items.add(Menu("다른 사람 리뷰 보기"))
        items.add(Menu("좋아요 목록"))
        items.add(Menu("공지사항"))
        items.add(Menu("설정"))
        items.add(Menu("삭제된 테마 검색"))
        items.add(Menu("졸업 현황 보기"))
    }

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(item: Menu) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }
    fun updateItem(pos: Int, item: Menu) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }
    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }
}