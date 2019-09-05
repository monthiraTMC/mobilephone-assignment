package com.scb.mobilephone.database

import com.scb.mobilephone.model.Mobiles

interface DatabaseInterface {
    interface DatabaseListener{
        fun getDBFavorite(list: ArrayList<Mobiles>)
        fun updateFavorite()
    }
    interface DatabasePresenter{
        fun addToFavorite(item: Mobiles)
        fun removeFavorite(item: Mobiles)
        fun getAllFavorite()
        fun setupDatabase()
    }
}