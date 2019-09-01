package com.scb.mobilephone.presenter

import android.content.Context
import com.scb.mobilephone.model.Mobiles

interface ListInterface {
    interface ListView {
        fun showLoading()

        fun hideLoading()

        fun showAllMobiles(mobileList: List<Mobiles>)

        fun getSortType(sortType:String)

        fun submitList(list: ArrayList<Mobiles>)

        fun reciveFavoriteList(favoriteList: ArrayList<Mobiles>)
    }

    interface ListPresenter {
        fun getMobileList()
        fun recieveBroadcast(context: Context)
        fun submitList(list: List<Mobiles>)
        fun getType(sortType: String)
        fun sendBroadcast(favoriteList: ArrayList<Mobiles>, context: Context)
    }

}
