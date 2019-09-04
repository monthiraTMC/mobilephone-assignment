package com.scb.mobilephone.lists

import android.content.Context
import com.scb.mobilephone.model.Mobiles

interface ListInterface {
    interface ListView {
        fun showLoading()

        fun hideLoading()

        fun submitList(list: ArrayList<Mobiles>)

        fun showAllMobiles(mobiles: ArrayList<Mobiles>)

        fun getAllFavorite(list: List<Mobiles>)

    }

    interface ListPresenter {
        fun getApiMobileList()
        fun getSortType(sortType: String)
        fun addToMobileList(mobiles: ArrayList<Mobiles>)
        fun gotoDetailPage(item: Mobiles)
        fun addToFavorite(item: Mobiles)
        fun removeFavorite(item: Mobiles)
        fun getAllFavorite()
        fun setupDatabase()

    }


}
