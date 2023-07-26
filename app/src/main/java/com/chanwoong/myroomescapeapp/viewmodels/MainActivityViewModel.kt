package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chanwoong.myroomescapeapp.model.BannerItem
import com.chanwoong.myroomescapeapp.model.GridItem

class MainActivityViewModel : ViewModel() {
    private val _gridItemList: MutableLiveData<List<GridItem>> = MutableLiveData()
    private val _bannerItemList: MutableLiveData<List<BannerItem>> = MutableLiveData()
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()

    val gridItemList: LiveData<List<GridItem>>
        get() = _gridItemList
    val bannerItemList: LiveData<List<BannerItem>>
        get() = _bannerItemList
    val currentPosition: LiveData<Int>
        get() = _currentPosition


    init{
        _currentPosition.value=0
    }

    fun setBannerItems(list: List<BannerItem>) {
        _bannerItemList.value = list
    }
    fun setGridItems(list: List<GridItem>) {
        _gridItemList.value = list
    }
    fun setCurrentPosition(position: Int){
        _currentPosition.value = position
    }

    fun getCurrentPosition() = currentPosition.value
}