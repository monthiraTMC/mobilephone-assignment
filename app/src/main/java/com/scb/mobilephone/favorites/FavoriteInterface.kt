package com.scb.mobilephone.favorites

import android.content.Context
import com.scb.mobilephone.model.Mobiles

interface FavoriteInterface {
    interface FavoriteView {
//        fun showLoading()
//
//        fun hideLoading()

        fun showAllFavorite(mobileList: ArrayList<Mobiles>)

        fun submitList(list: ArrayList<Mobiles>)

        fun getSortType(sortType: String)


    }

    interface FavoritePresenter {
//        fun getMobileList()

        fun recieveBroadcast(context: Context)

        fun getType(sortType: String)

        fun submitList(list: ArrayList<Mobiles>)

        fun sendBroadcastMessage(mFavoriteArray: ArrayList<Mobiles>, context: Context)
    }

}
