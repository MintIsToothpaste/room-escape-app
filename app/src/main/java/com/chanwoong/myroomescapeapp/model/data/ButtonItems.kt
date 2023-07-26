package com.chanwoong.myroomescapeapp.model.data

import com.chanwoong.myroomescapeapp.R
import com.chanwoong.myroomescapeapp.model.BannerItem
import com.chanwoong.myroomescapeapp.model.GridItem

val buttonBannerItemList = listOf(
    BannerItem(R.drawable.salebanner),
    BannerItem(R.drawable.salebanner2),
    BannerItem(R.drawable.salebanner3)
)

val buttonGridItemList = listOf(
    GridItem(R.drawable.gangnam, "강남"),
    GridItem(R.drawable.hongdae, "홍대"),
    GridItem(R.drawable.sinchon, "신촌"),
    GridItem(R.drawable.konkuk, "건대"),
    GridItem(R.drawable.universitystreet, "대학로"),
    GridItem(R.drawable.newtheme, "신규테마"),
    GridItem(R.drawable.easy, "쉬운 테마"),
    GridItem(R.drawable.hard, "어려운 테마"),
    GridItem(R.drawable.fear, "공포"),
    GridItem(R.drawable.ban, "19금")
    /*GridItem(R.drawable.k, "이색 컨텐츠"),
    GridItem(R.drawable.l, "지도보기"),
    GridItem(R.drawable.m, "커뮤니티"),
    GridItem(R.drawable.n, "이벤트"),
    GridItem(R.drawable.o, "문의하기"), //Last Item is Blank*/
)