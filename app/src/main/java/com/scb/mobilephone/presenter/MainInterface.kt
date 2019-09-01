package com.scb.mobilephone.presenter;

interface MainInterface {
    interface MainView {
        fun getSortType(sortType:String)
    }

    interface MainPresenter {
        fun showDialog()
    }
}
