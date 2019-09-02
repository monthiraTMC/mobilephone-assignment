package com.scb.mobilephone.helper

import android.util.Log
import com.scb.mobilephone.model.Mobiles




class SortPresenter {
    private var mFillterArray:ArrayList<Mobiles> = ArrayList()
    private var _mobiles: List<Mobiles> = listOf()

    fun sortMobileList(list: ArrayList<Mobiles>, sortType: String): ArrayList<Mobiles> {
        _mobiles = list
        mFillterArray.clear()
        when (sortType) {
            "Price low to high" -> {
                mFillterArray.addAll(_mobiles.sortedBy { it.price })
            }
            "Price high to low" -> {
                mFillterArray.addAll(_mobiles.sortedByDescending { it.price })
            }
            "Rating 5-1" -> {
                mFillterArray.addAll(_mobiles.sortedByDescending { it.rating })
            }
            else -> {
                mFillterArray.addAll(_mobiles)
            }
        }
        Log.d("sortFillter", sortType)
        Log.d("sortFillter", mFillterArray.toString())

     return mFillterArray
    }

}