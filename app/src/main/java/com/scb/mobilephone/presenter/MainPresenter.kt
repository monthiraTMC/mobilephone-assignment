package com.scb.mobilephone.presenter;

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.view.FavoriteFragment
import com.scb.mobilephone.view.ListFragment

class MainPresenter(_context: Context) : MainInterface.MainPresenter {
    override fun getPosition(position: Int) {
        this.positionTab = position
    }

    private val context = _context
    private var positionTab = 0
    private var mFavoriteFragment: FavoriteFragment = FavoriteFragment()
    private var mListFragment: ListFragment = ListFragment()

    override fun showDialog() {
        val mBuilder = AlertDialog.Builder(context)
        val listItems = context.getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            var sortType = listItems[i]
            context.showToast(listItems[i].toString())
            if (positionTab == 0) {
                mListFragment.getSortType(sortType)
            }
            else {
                mFavoriteFragment.getSortType(sortType)
            }
            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }


}