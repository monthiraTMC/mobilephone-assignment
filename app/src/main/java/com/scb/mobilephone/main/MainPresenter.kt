package com.scb.mobilephone.main

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.ui.main.SectionsPagerAdapter

class MainPresenter(
    private val context: Context,
    private val fragmentAdapter: SectionsPagerAdapter
) : MainInterface.MainPresenter {

    private var checkedItem = -1

    override fun showDialog() {
        val mBuilder = AlertDialog.Builder(context)
        val listItems = context.getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, checkedItem) { dialogInterface, i ->
            val sortType = listItems[i]
            checkedItem = i
            fragmentAdapter.getSortType(sortType)
            context.showToast(listItems[i].toString())
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }
    
}


