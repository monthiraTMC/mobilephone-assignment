package com.scb.mobilephone.favorites

import com.scb.mobilephone.model.Mobiles

interface FavoriteInterface {
    interface FavoriteView {

        fun showAllFavorite(mobileList: ArrayList<Mobiles>)

        fun submitList(list: ArrayList<Mobiles>)

        fun getSortType(sortType: String)


    }

    interface FavoritePresenter {

        fun getType(sortType: String)

        fun submitList(list: ArrayList<Mobiles>)

    }


}
