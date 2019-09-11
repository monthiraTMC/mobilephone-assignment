package com.scb.mobilephone.mobilefavorites

import com.scb.mobilephone.model.Mobiles

interface FavoriteInterface {
    interface FavoritePresenter{
        fun getType(sortType: String)
        fun getFavorite(list: ArrayList<Mobiles>)
    }
}