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

//        fun getFavoriteItem(list: ArrayList<Mobiles>)

    }

    interface ListPresenter {
//        fun getFavoriteItem(list: ArrayList<Mobiles>)
        fun getApiMobileList()
        fun addToMobileList(mobiles: ArrayList<Mobiles>)
        fun submitList(list: List<Mobiles>)
        fun getType(sortType: String)
        fun gotoDetailPage(item: Mobiles)

    }


}
