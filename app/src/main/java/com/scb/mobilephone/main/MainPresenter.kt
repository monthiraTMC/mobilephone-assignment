package com.scb.mobilephone.main;

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.favorites.FavoriteFragment
import com.scb.mobilephone.lists.ListFragment
import com.scb.mobilephone.ui.main.SectionsPagerAdapter

class MainPresenter(private val context: Context, private val view: MainInterface.MainView,
                    private val fragmentAdapter: SectionsPagerAdapter) : MainInterface.MainPresenter{

    override fun getPosition(position: Int) {
        this.positionTab = position
        if (positionTab == 0){
            fragmentAdapter.updateFavorite()
        }
        else {
            fragmentAdapter.updateFavorite()
        }
    }

    private var positionTab = 0


    override fun showDialog() {
        val mBuilder = AlertDialog.Builder(context)
        val listItems = context.getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            var sortType = listItems[i]
            fragmentAdapter.getSortType(sortType)
            context.showToast(listItems[i].toString())
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

}


