package com.scb.mobilephone.presenter;

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.MobileDetail
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.view.ListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(_view : MainInterface.MainView, _context: Context, position: Int): MainInterface.MainPresenter {
    private val view = _view
    private val positionTab = position
    private val context = _context

    override fun showDialog() {
        val mBuilder = AlertDialog.Builder(context)
        val listItems = context.getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            //                mResult.setText(listItems[i])

            var sortType = listItems[i]

            context.showToast(listItems[i].toString())
            if(positionTab == 0) {
                view.getSortType(sortType)
            }
            else {
//                mFavoriteFragment.getType(sortType)
            }

            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }



}