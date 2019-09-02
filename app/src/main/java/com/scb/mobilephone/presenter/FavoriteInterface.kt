package com.scb.mobilephone.presenter

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

//        fun reciveFavoriteList(favoriteList: ArrayList<Mobiles>)

    }

    interface FavoritePresenter {
        fun getMobileList()

        fun recieveBroadcast(context: Context)

        fun getType(sortType: String)

        fun submitList(list: ArrayList<Mobiles>)
    }

}
