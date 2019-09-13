package com.scb.mobilephone.mobiledetails

interface DetailInterface {
    interface DetailView {
        fun showImageDetail(imageURL: ArrayList<String>)
        fun showToastMessage(message:String)
        fun showDialog()
    }

    interface DetailPresenter {
        fun getDetail(mobile_id:Int)
    }
}
