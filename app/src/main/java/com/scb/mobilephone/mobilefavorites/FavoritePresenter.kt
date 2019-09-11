package com.scb.mobilephone.mobilefavorites

import com.scb.mobilephone.helper.SortInterface
import com.scb.mobilephone.helper.SortList
import com.scb.mobilephone.model.Mobiles

class FavoritePresenter(view: SortInterface.SortToView): FavoriteInterface.FavoritePresenter {
    override fun getType(sortType: String) {
        this.mSortType = sortType
        sortPresenter.sortMobileList(mSortType, mFavoriteArray)
    }

    override fun getFavorite(list: ArrayList<Mobiles>) {
        this.mFavoriteArray = list
        sortPresenter.sortMobileList(mSortType, mFavoriteArray)
    }

    private var mSortType = "none"
    private var mFavoriteArray: ArrayList<Mobiles> = ArrayList()
    private var sortPresenter: SortInterface.SortPresenter = SortList(view)
}