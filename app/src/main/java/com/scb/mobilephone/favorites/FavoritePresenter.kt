package com.scb.mobilephone.favorites

import com.scb.mobilephone.helper.CustomItemTouchHelperListener
import com.scb.mobilephone.helper.FavoriteDataArrayInterface
import com.scb.mobilephone.helper.SortList
import com.scb.mobilephone.model.Mobiles
import kotlin.NullPointerException as NullPointerException1

class FavoritePresenter(_view: FavoriteInterface.FavoriteView) :
    FavoriteInterface.FavoritePresenter {

    private lateinit var  mReceiveFillterArray: ArrayList<Mobiles>
    var _favoriteItem: ArrayList<Mobiles> = ArrayList()
    var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()
    private var mSortPresenter: SortList = SortList()
    var view = _view
    private var mSortType: String = "none"

    override fun getType(sortType: String) {
        mSortType = sortType
        sort()
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        _favoriteItem = list
        mFavoriteDataArray.clear()
        mFavoriteDataArray.addAll(_favoriteItem)
        sort()
    }

    fun sort() {
        mReceiveFillterArray = mSortPresenter.sortMobileList(mFavoriteDataArray, mSortType)
        view.submitList(mReceiveFillterArray)
    }

}