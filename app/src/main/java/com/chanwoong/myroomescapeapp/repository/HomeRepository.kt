package com.chanwoong.myroomescapeapp.repository

import com.chanwoong.myroomescapeapp.model.BannerItem
import com.chanwoong.myroomescapeapp.model.GridItem

interface HomeRepository {
    suspend fun getBannerItems(): List<BannerItem>
    suspend fun getGridItems(): List<GridItem>
}