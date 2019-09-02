package com.scb.mobilephone.main;

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.favorites.FavoriteFragment
import com.scb.mobilephone.lists.ListFragment

class MainPresenter(_view: MainInterface.MainView, _context: Context) : MainInterface.MainPresenter {

    override fun getSortType(sortType: String) {
        this.mSortType = sortType
        sortMobiles()
    }

    override fun getPosition(position: Int) {
        this.positionTab = position
        sortMobiles()
        Log.d("mSort4", positionTab.toString())
    }

    private var view = _view
    private val context = _context
    private var mSortType = "none"
    private var positionTab = 0
    private var mFavoriteFragment: FavoriteFragment = FavoriteFragment()
    private var mListFragment: ListFragment = ListFragment()

    override fun showDialog() {
        val mBuilder = AlertDialog.Builder(context)
        val listItems = context.getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            var sortType = listItems[i]
            view.getSortType(sortType)
            context.showToast(listItems[i].toString())
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    fun sortMobiles() {
        if (positionTab == 0) {
            mListFragment.getSortType(mSortType)

        } else {
            mFavoriteFragment.getSortType(mSortType)
        }
    }


}