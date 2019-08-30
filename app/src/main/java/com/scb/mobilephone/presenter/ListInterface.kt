package com.scb.mobilephone.presenter

import com.scb.mobilephone.model.Mobiles

interface ListInterface {
    interface ListView {
        fun showLoading()

        fun hideLoading()

        fun showAllMobiles(mobileList: List<Mobiles>, sortType:String)

//        fun navigateToDetailPage(mobileList: ArrayList<Mobiles>)
    }

    interface ListPresenter {
        fun getMobileList(sortType: String)

    }

}
