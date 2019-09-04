package com.scb.mobilephone.main;

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.favorites.FavoriteFragment
import com.scb.mobilephone.lists.ListFragment

class MainPresenter(_context: Context,
    _listView: ListFragment,
    _favoriteView: FavoriteFragment
) : MainInterface.MainPresenter{

    override fun getPosition(position: Int) {
        this.positionTab = position
//        sortMobiles()
        Log.d("mSort4", positionTab.toString())
    }

    private val context = _context
    private var mSortType = "none"
    private var positionTab = 0
    private var listView = _listView
    private var favoriteView = _favoriteView


    override fun showDialog() {
        val mBuilder = AlertDialog.Builder(context)
        val listItems = context.getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            var sortType = listItems[i]
            if (positionTab == 0) {
                listView.getSortType(sortType)

            } else {
                favoriteView.getSortType(sortType)
            }
            context.showToast(listItems[i].toString())
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    interface ViewListener{
        fun getSortType(sortType:String)
    }

}


