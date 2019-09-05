package com.scb.mobilephone.lists

import android.content.Context
import com.scb.mobilephone.model.Mobiles

interface ListInterface {
    interface ListView {
        fun showLoading()

        fun hideLoading()

        fun submitList(list: ArrayList<Mobiles>)

        fun showAllMobiles(mobiles: ArrayList<Mobiles>)

//        fun getAllFavorite(mobiles: ArrayList<Mobiles>)

    }

    interface ListPresenter {
        fun getApiMobileList()
        fun gotoDetailPage(item: Mobiles)
    }

}
