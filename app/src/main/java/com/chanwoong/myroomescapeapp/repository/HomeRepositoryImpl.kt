package com.chanwoong.myroomescapeapp.repository

import com.chanwoong.myroomescapeapp.model.BannerItem
import com.chanwoong.myroomescapeapp.model.GridItem
import com.chanwoong.myroomescapeapp.model.data.buttonBannerItemList
import com.chanwoong.myroomescapeapp.model.data.buttonGridItemList

//singleton
object HomeRepositoryImpl : HomeRepository {
    override suspend fun getBannerItems(): List<BannerItem> {
        return buttonBannerItemList
    }

    override suspend fun getGridItems(): List<GridItem> {
        return buttonGridItemList
    }

}