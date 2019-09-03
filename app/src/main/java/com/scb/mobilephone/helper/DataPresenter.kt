package com.scb.mobilephone.helper

import android.util.Log
import com.scb.mobilephone.favorites.FavoriteFragment
import com.scb.mobilephone.favorites.FavoriteInterface
import com.scb.mobilephone.favorites.FavoritePresenter
import com.scb.mobilephone.lists.ListInterface
import com.scb.mobilephone.model.Mobiles

interface FavoriteDataArrayInterface {
    interface PresentToView {
        fun getFavorite(list: ArrayList<Mobiles>)
    }

    interface AddFavoritePresenter {
        fun addToFavorite(item: Mobiles)
        fun removeFavorite(item: Mobiles)
    }

}


class AddFavorite(
    _listView: FavoriteDataArrayInterface.PresentToView,
    _favoriteView: FavoriteDataArrayInterface.PresentToView
) : FavoriteDataArrayInterface.AddFavoritePresenter {
    override fun addToFavorite(item: Mobiles) {
        mFavoriteArray.add(item)
        listView.getFavorite(mFavoriteArray)
        favoriteView.getFavorite(mFavoriteArray)
        Log.d("mFavorite", mFavoriteArray.toString())
    }

    override fun removeFavorite(item: Mobiles) {
        mFavoriteArray.remove(item)
        listView.getFavorite(mFavoriteArray)
        favoriteView.getFavorite(mFavoriteArray)
        Log.d("mFavorite", mFavoriteArray.toString())

    }

    private val listView = _listView
    private val favoriteView = _favoriteView
    private var mFavoriteArray: ArrayList<Mobiles> = ArrayList()

}

class SortList {
    private var mSortArray: ArrayList<Mobiles> = ArrayList()
    private var _mobiles: List<Mobiles> = listOf()
    fun sortMobileList(list: ArrayList<Mobiles>, sortType: String): ArrayList<Mobiles> {
        _mobiles = list
        mSortArray.clear()
        when (sortType) {
            "Price low to high" -> {
                mSortArray.addAll(_mobiles.sortedBy { it.price })
            }
            "Price high to low" -> {
                mSortArray.addAll(_mobiles.sortedByDescending { it.price })
            }
            "Rating 5-1" -> {
                mSortArray.addAll(_mobiles.sortedByDescending { it.rating })
            }
            else -> {
                mSortArray.addAll(_mobiles)
            }
        }
        Log.d("sortFillter", sortType)
        Log.d("sortFillter", mSortArray.toString())

        return mSortArray
    }

}