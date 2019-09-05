package com.scb.mobilephone.favorites

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scb.mobilephone.database.AppDatabase
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.helper.CMWorkerThread
import com.scb.mobilephone.helper.CustomItemTouchHelperListener
import com.scb.mobilephone.helper.SortList
import com.scb.mobilephone.lists.ListInterface
import com.scb.mobilephone.model.Mobiles
import kotlin.NullPointerException as NullPointerException1

class FavoritePresenter(private val view: FavoriteInterface.FavoriteView, private val context: Context) :
    FavoriteInterface.FavoritePresenter{



    private var mSortType: String = "none"
    private var mDatabaseAdapter: AppDatabase? = null
    private var mFavoriteArray: ArrayList<Mobiles> = arrayListOf()


}