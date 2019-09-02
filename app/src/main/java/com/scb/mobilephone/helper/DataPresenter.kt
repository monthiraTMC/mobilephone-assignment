package com.scb.mobilephone.helper

import android.util.Log
import com.scb.mobilephone.model.Mobiles

interface FavoriteDataArray{
    fun addToFavorite(item : Mobiles)
    fun removeFavorite(item: Mobiles)

}


class DataPresenter(private val listener: FavoriteDataListener): FavoriteDataArray {


    override fun addToFavorite(item: Mobiles) {
        mFavoriteArray.add(item)
        listener.getFavorite(mFavoriteArray)
        Log.d("mFavorite", mFavoriteArray.toString())
    }

    override fun removeFavorite(item: Mobiles) {
        mFavoriteArray.remove(item)
        listener.getFavorite(mFavoriteArray)
        Log.d("mFavorite", mFavoriteArray.toString())

    }

    private var mSortArray:ArrayList<Mobiles> = ArrayList()
    private var mFavoriteArray:ArrayList<Mobiles> = ArrayList()
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

    interface FavoriteDataListener{
        fun getFavorite(list : ArrayList<Mobiles>)
    }

}