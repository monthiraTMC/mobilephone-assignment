package com.scb.mobilephone.mobilelists

import com.scb.mobilephone.model.Mobiles

interface MobileListInterface {
    interface MobileListView {
        fun showLoading()
        fun hideLoading()
        fun showAllMobiles(mobiles: ArrayList<Mobiles>)
        fun closeApp()
    }

    interface MobileListPresenter {
        fun getApiMobileList()
        fun gotoDetailPage(item: Mobiles)
    }
}
