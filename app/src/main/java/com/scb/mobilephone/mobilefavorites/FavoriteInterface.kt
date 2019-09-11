package com.scb.mobilephone.mobilefavorites

import com.scb.mobilephone.model.Mobiles

interface FavoriteInterface {
    interface FavoriteView{

    }
    interface FavoritePresenter{
        fun getType(sortType: String)
        fun getFavorite(list: ArrayList<Mobiles>)
    }
}