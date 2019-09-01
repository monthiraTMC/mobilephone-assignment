//package com.scb.mobilephone.presenter
//
//import android.util.Log
//import android.view.View
//
//interface SortInterface {
//    interface SortView {
//        fun sortType(sortType:String)
//    }
//
//    interface SortPresenter {
//        fun getSortType(sortType: String)
//
//    }
//}
//
//
//
//class SortPresenter(_view: SortInterface.SortView): SortInterface.SortPresenter {
//    private var view = _view
//    override fun getSortType(sortType: String) {
//        view.sortType(sortType)
//        Log.d("sortpresenter", sortType)
//    }
//
//}