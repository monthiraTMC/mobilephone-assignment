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

        fun getAllFavorite(list: List<Mobiles>)

    }

    interface ListPresenter {
        fun getApiMobileList()
        fun addToMobileList(mobiles: ArrayList<Mobiles>)
        fun getType(sortType: String)
        fun gotoDetailPage(item: Mobiles)
        fun addToFavorite(item: Mobiles)
        fun removeFavorite(item: Mobiles)
        fun getAllFavorite()
        fun setupDatabase()

    }


}
