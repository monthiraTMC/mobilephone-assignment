package com.scb.mobilephone.datails;

interface DetailInterface {
    interface DetailView {
        fun showImageDetail(mobileList: ArrayList<String>)
    }

    interface DetailPresenter {
        fun getDetail(mobile_id:Int)
    }
}
