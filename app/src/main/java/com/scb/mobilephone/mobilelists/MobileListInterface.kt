package com.scb.mobilephone.mobilelists

import com.scb.mobilephone.model.Mobiles

interface MobileListInterface {
    interface MobileListView {
        fun showLoading()
        fun hideLoading()
        fun getAllMobiles(mobiles: ArrayList<Mobiles>)
        fun showDialog()
        fun showToastMessage(message:String)
        fun notifyFavoriteChange(list: ArrayList<Mobiles>)
    }

    interface MobileListPresenter {
        fun getApiMobileList()
        fun showAllMobile(list: ArrayList<Mobiles>)
        fun getFavorite(list: ArrayList<Mobiles>)
        fun getSortType(sortType:String)
    }
}
