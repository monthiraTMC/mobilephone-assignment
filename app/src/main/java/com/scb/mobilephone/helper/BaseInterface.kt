package com.scb.mobilephone.helper

import com.scb.mobilephone.model.Mobiles

interface SortInterface {
    fun getSortType(sortType: String)
}

interface UpdateInterface {
    fun updateToFavorite(list: ArrayList<Mobiles>)
    fun getUpdateFavorite():ArrayList<Mobiles>
}