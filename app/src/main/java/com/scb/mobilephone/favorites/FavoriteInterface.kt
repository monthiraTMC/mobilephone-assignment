package com.scb.mobilephone.favorites

import com.scb.mobilephone.model.Mobiles

interface FavoriteInterface {
    interface FavoriteView {

        fun getAllFavorite(list: ArrayList<Mobiles>)

        fun submitList(list: ArrayList<Mobiles>)

    }

    interface FavoritePresenter {

        fun getType(sortType: String)

        fun getAllFavorite()

        fun removeFavorite(item: Mobiles)

        fun setupDatabase()

        fun updateFavorite(list: ArrayList<Mobiles>)

    }


}
