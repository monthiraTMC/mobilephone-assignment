package com.scb.mobilephone.lists

import com.scb.mobilephone.model.Mobiles

interface ListInterface {
    interface ListView {
        fun showLoading()
        fun hideLoading()
        fun showAllMobiles(mobiles: ArrayList<Mobiles>)
    }

    interface ListPresenter {
        fun getApiMobileList()
        fun gotoDetailPage(item: Mobiles)
    }
}
