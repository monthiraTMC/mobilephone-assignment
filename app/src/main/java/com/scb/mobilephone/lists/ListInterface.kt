package com.scb.mobilephone.lists

import android.content.Context
import com.scb.mobilephone.model.Mobiles

interface ListInterface {
    interface ListView {
        fun showLoading()

        fun hideLoading()

        fun showAllMobiles(mobileList: ArrayList<Mobiles>)

        fun getSortType(sortType:String)

        fun submitList(list: ArrayList<Mobiles>)

        fun reciveFavoriteList(favoriteList: ArrayList<Mobiles>)
    }

    interface ListPresenter {
        fun getApiMobileList()
        fun addToMobileList(mobiles: ArrayList<Mobiles>)
        fun recieveBroadcast(context: Context)
        fun submitList(list: List<Mobiles>)
        fun getType(sortType: String)
        fun sendBroadcast(favoriteList: ArrayList<Mobiles>, context: Context)
        fun gotoDetailPage(item: Mobiles)
    }

}
