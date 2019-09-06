package com.scb.mobilephone.mobiledetails

interface DetailInterface {
    interface DetailView {
        fun showImageDetail(mobileList: ArrayList<String>)
        fun closeApp()
    }

    interface DetailPresenter {
        fun getDetail(mobile_id:Int)
    }
}
