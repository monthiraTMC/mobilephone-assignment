package com.scb.mobilephone.main;


interface MainInterface {
    interface MainView {
//        fun getSortType(sortType:String)
    }

    interface PresentToListView{
        fun getSortType(sortType:String)
    }

    interface PresentToFavoriteView{
        fun getSortType(sortType:String)
    }

    interface MainPresenter {
        fun showDialog()
        fun getPosition(position: Int)
//        fun getSortType(sortType:String)
    }
}

