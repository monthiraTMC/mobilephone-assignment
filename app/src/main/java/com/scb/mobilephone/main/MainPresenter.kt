package com.scb.mobilephone.main;

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.ui.main.SectionsPagerAdapter

class MainPresenter(private val context: Context, private val view: MainInterface.MainView,
                    private val fragmentAdapter: SectionsPagerAdapter) : MainInterface.MainPresenter{

    override fun getPosition(position: Int) {
        this.positionTab = position
           if (position == 1){
                    fragmentAdapter.updateFavorite()
                }
                else{
                    fragmentAdapter.updateFavorite()
                }
    }

    private var positionTab = 0


    override fun showDialog() {
        val mBuilder = AlertDialog.Builder(context)
        val listItems = context.getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            var sortType = listItems[i]
            mSortType = sortType
            fragmentAdapter.getSortType(sortType)
            context.showToast(listItems[i].toString())
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private  var mSortType = "none"

}


