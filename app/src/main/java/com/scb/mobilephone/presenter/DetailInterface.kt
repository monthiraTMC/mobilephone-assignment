package com.scb.mobilephone.presenter;

interface DetailInterface {
    interface DetailView {
        fun showImageDetail(mobileList: ArrayList<String>)

        fun showLoading()

        fun hideLoading()
    }

    interface DetailPresenter {
        fun getDetail(mobile_id:Int)
    }
}
