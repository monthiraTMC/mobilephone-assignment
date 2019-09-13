package com.scb.mobilephone.helper

import com.scb.mobilephone.extensions.*
import com.scb.mobilephone.model.Mobiles

interface SortInterface {
    interface SortToView{
        fun submitList(list: ArrayList<Mobiles>)
        fun getSortType(sortType: String)
    }

    interface SortPresenter {
        fun sortMobileList(sortType: String, list: ArrayList<Mobiles>)
    }
}
class SortList(private val view: SortInterface.SortToView): SortInterface.SortPresenter {

    private var mSortArray: ArrayList<Mobiles> = ArrayList()
    private var _mobiles: List<Mobiles> = listOf()

    override fun sortMobileList(sortType: String, list: ArrayList<Mobiles>) {
        _mobiles = list
        mSortArray.clear()
        when (sortType) {
            LOW_TO_HIGH -> {
                mSortArray.addAll(_mobiles.sortedBy { it.price })
            }
            HIGH_TO_LOW -> {
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
