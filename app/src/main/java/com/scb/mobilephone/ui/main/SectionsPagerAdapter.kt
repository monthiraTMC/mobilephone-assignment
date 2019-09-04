package com.scb.mobilephone.ui.main

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.scb.mobilephone.favorites.FavoriteFragment
import com.scb.mobilephone.lists.ListFragment
import com.scb.mobilephone.main.MainInterface

private val TAB_TITLES = arrayOf("Mobile list", "Favolite list")

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    private var mFavoriteFragment: FavoriteFragment = FavoriteFragment()
    private var mListFragment: ListFragment = ListFragment()
    var mSortType = "none"
    override fun getItem(position: Int): Fragment {

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when (position) {
            0 -> {
                Log.d("mSortType", mSortType)
                mListFragment
            }
            else-> {
                Log.d("mSortType", mSortType)
                mFavoriteFragment
            }
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