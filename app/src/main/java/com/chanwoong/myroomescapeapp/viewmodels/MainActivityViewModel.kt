package com.chanwoong.myroomescapeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chanwoong.myroomescapeapp.model.GridItem

class MainActivityViewModel : ViewModel() {
    private val _gridItemList: MutableLiveData<List<GridItem>> = MutableLiveData()

    val gridItemList: LiveData<List<GridItem>>
        get() = _gridItemList

    fun setGridItems(list: List<GridItem>) {
        _gridItemList.value = list
    }
}