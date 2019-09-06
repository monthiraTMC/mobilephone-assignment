package com.scb.mobilephone.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.scb.mobilephone.database.DatabaseInterface
import com.scb.mobilephone.helper.SortInterface
import com.scb.mobilephone.mobilefavorites.FavoriteFragment
import com.scb.mobilephone.mobilelists.MobileListFragment

private val TAB_TITLES = arrayOf("Mobile List", "Favorite List")

class SectionsPagerAdapter(
    private val fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {
    fun getSortType(sortType: String) {
        val fragments = fragmentManager.fragments
        fragments.forEach {
            if (it is SortInterface.SortToView) {
                it.getSortType(sortType)
            }
        }
    }

    fun updateFavorite() {
        val fragments = fragmentManager.fragments
        fragments.forEach {
            if (it is DatabaseInterface.DatabaseListener) {
                it.updateFavorite()
            }
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> FavoriteFragment()
            else -> MobileListFragment()

        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}