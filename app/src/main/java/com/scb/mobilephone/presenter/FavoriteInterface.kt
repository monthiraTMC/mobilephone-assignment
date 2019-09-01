package com.scb.mobilephone.presenter

import android.content.Context
import com.scb.mobilephone.model.Mobiles

interface FavoriteInterface {
    interface FavoriteView {
//        fun showLoading()
//
//        fun hideLoading()

        //        fun showAllFavorite(mobileList: List<Mobiles>)
        fun getSortType(sortType: String)
    }

    interface FavoritePresenter {
        //        fun getMobileList(sortType: String)
        fun recieveBroadcast(context: Context)

        fun getType(sortType: String)
    }

}
