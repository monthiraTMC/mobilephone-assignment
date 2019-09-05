package com.scb.mobilephone.mobilelists

import com.scb.mobilephone.model.Mobiles

interface MobileListInterface {
    interface MobileListView {
        fun showLoading()
        fun hideLoading()
        fun showAllMobiles(mobiles: ArrayList<Mobiles>)
    }

    interface MobileListPresenter {
        fun getApiMobileList()
        fun gotoDetailPage(item: Mobiles)
    }
}
