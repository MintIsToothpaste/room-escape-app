package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanwoong.myroomescapeapp.model.BannerItem
import com.chanwoong.myroomescapeapp.model.GridItem
import com.chanwoong.myroomescapeapp.repository.HomeRepositoryImpl
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val _bannerItemList: MutableLiveData<List<BannerItem>> = MutableLiveData()
    private val _gridItemList: MutableLiveData<List<GridItem>> = MutableLiveData()
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()

    val itemClickEvent = MutableLiveData<Int>()

    val bannerItemList: LiveData<List<BannerItem>>
        get() = _bannerItemList
    val gridItemList: LiveData<List<GridItem>>
        get() = _gridItemList
    val currentPosition: LiveData<Int>
        get() = _currentPosition

    init {
        _currentPosition.value = 0
        itemClickEvent.value = -1
    }

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun getCurrentPosition() = currentPosition.value

    fun getBannerItems() {
        viewModelScope.launch {
            val bannerItemLiveData = HomeRepositoryImpl.getBannerItems()
            withContext(Main) {
                _bannerItemList.value = bannerItemLiveData
            }
        }
    }

    fun getGridItems() {
        viewModelScope.launch {
            val gridItemLiveData = HomeRepositoryImpl.getGridItems()
            withContext(Main) {
                _gridItemList.value = gridItemLiveData
            }
        }
    }
}