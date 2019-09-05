package com.scb.mobilephone.mobiledatails;

interface DetailInterface {
    interface DetailView {
        fun showImageDetail(mobileList: ArrayList<String>)
    }

    interface DetailPresenter {
        fun getDetail(mobile_id:Int)
    }
}
