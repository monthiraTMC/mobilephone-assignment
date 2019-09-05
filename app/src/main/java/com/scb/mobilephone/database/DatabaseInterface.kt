package com.scb.mobilephone.database

import com.scb.mobilephone.model.Mobiles

interface DatabaseInterface {
    interface DatabaseToView {
        fun showUpdateFavoriteList(list: ArrayList<Mobiles>)
    }
    interface DatabasePresenter{
        fun addToFavorite(item: Mobiles)
        fun removeFavorite(item: Mobiles)
        fun getAllFavorite()
        fun setupDatabase()
    }
}