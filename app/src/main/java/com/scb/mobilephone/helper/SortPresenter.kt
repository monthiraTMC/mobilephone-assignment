package com.scb.mobilephone.helper

import android.util.Log
import com.scb.mobilephone.extensions.HIGHT_TO_LOW
import com.scb.mobilephone.extensions.LOW_TO_HIGHT
import com.scb.mobilephone.extensions.RATING_5_1
import com.scb.mobilephone.favorites.FavoriteInterface
import com.scb.mobilephone.lists.ListInterface
import com.scb.mobilephone.model.Mobiles

interface SortPresenter {
    fun sortMobileList(sortType: String, list: ArrayList<Mobiles>)
}
class SortList(private val view: ListInterface.ListView): SortPresenter {
    private var mSortArray: ArrayList<Mobiles> = ArrayList()
    private var _mobiles: List<Mobiles> = listOf()
    override fun sortMobileList(sortType: String, list: ArrayList<Mobiles>) {
        _mobiles = list
        mSortArray.clear()
        when (sortType) {
            LOW_TO_HIGHT -> {
                mSortArray.addAll(_mobiles.sortedBy { it.price })
            }
            HIGHT_TO_LOW -> {
                mSortArray.addAll(_mobiles.sortedByDescending { it.price })
            }
            RATING_5_1 -> {
                mSortArray.addAll(_mobiles.sortedByDescending { it.rating })
            }
            else -> {
                mSortArray.addAll(_mobiles)
            }
        }
        view.submitList(mSortArray)
    }

}

class SortFavoriteList(private val view: FavoriteInterface.FavoriteView): SortPresenter {
    private var mSortArray: ArrayList<Mobiles> = ArrayList()
    private var _mobiles: List<Mobiles> = listOf()
    override fun sortMobileList(sortType: String, list: ArrayList<Mobiles>) {
        _mobiles = list
        mSortArray.clear()
        when (sortType) {
            LOW_TO_HIGHT -> {
                mSortArray.addAll(_mobiles.sortedBy { it.price })
            }
            HIGHT_TO_LOW -> {
                mSortArray.addAll(_mobiles.sortedByDescending { it.price })
            }
            RATING_5_1 -> {
                mSortArray.addAll(_mobiles.sortedByDescending { it.rating })
            }
            else -> {
                mSortArray.addAll(_mobiles)
            }
        }
        view.submitList(mSortArray)
    }

}