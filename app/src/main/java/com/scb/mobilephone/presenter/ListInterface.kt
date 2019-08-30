package com.scb.mobilephone.presenter

import android.content.Context
import com.scb.mobilephone.model.Mobiles

interface ListInterface {
    interface ListView {
        fun showLoading()

        fun hideLoading()

        fun showAllMobiles(mobileList: List<Mobiles>, sortType:String)

    }

    interface ListPresenter {
        fun getMobileList(sortType: String)
        fun recieveBroadcast(context: Context)
    }

}
