package com.scb.mobilephone.ui.main

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.scb.mobilephone.favorites.FavoriteFragment
import com.scb.mobilephone.helper.SortInterface
import com.scb.mobilephone.helper.UpdateInterface
import com.scb.mobilephone.lists.ListFragment
import com.scb.mobilephone.main.MainInterface
import com.scb.mobilephone.model.Mobiles

private val TAB_TITLES = arrayOf("Mobile list", "Favolite list")

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

class SectionsPagerAdapter(
    val fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager){
    fun getSortType(sortType: String) {
        val fragments = fragmentManager.fragments
        fragments.forEach {
            if (it is SortInterface) {
                it.getSortType(sortType)
            }
        }
    }

    fun getAllFavorite():ArrayList<Mobiles>? {
        val fragments = fragmentManager.fragments
        fragments.forEach {
            if (it is ListFragment) {
                return it.getUpdateFavorite()
            }
        }
        return null
    }


//    fun setFavoriteMobile(){
//        val favoriteList = getAllFavorite()
//        val fragments = fragmentManager.fragments
//        fragments.forEach {
//            if (it is FavoriteFragment) {
//                it.getAllFavorite(favoriteList!!)
//            }
//        }
//    }

    fun setToUpdateFavorite():ArrayList<Mobiles>? {
        val fragments = fragmentManager.fragments
        fragments.forEach {
            if (it is UpdateInterface) {
                return it.getUpdateFavorite()
            }

        }
        return null
    }

    fun updateFavorite(){
        val favoriteList = setToUpdateFavorite()
        val fragments = fragmentManager.fragments
        fragments.forEach {
            if (it is UpdateInterface) {
                it.updateToFavorite(favoriteList!!)
            }
        }
    }
//
//    fun setUnFavoriteMobile(){
//        val unFavoriteMobile = getUnFavoriteMobile()
//        val fragments = fragmentManager.fragments
//        fragments.forEach {
//            if (it is MobileListFragment) {
//                it.checkUnFav(unFavoriteMobile)
//            }
//        }
//    }
    private var mFavoriteFragment: FavoriteFragment = FavoriteFragment()
    private var mListFragment: ListFragment = ListFragment()
    var mSortType = "none"
    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> mFavoriteFragment
            else -> mListFragment

        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }


}