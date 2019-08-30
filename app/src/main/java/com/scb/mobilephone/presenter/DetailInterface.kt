package com.scb.mobilephone.presenter;

import com.scb.mobilephone.model.Mobiles

interface DetailInterface {
    interface DetailView {
        fun showImageDetail(mobileList: ArrayList<String>)
    }

    interface DetailPresenter {
        fun getDetail(mobile_id:Int)
    }
}
