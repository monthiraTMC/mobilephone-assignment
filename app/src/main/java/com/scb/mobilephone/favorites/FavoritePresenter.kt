package com.scb.mobilephone.favorites

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE_LIST
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.helper.SortList
import kotlin.NullPointerException as NullPointerException1

class FavoritePresenter(_view: FavoriteInterface.FavoriteView) :
    FavoriteInterface.FavoritePresenter {

    private lateinit var  mReceiveFillterArray: ArrayList<Mobiles>
    var _favoriteItem: ArrayList<Mobiles> = ArrayList()
    var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()
    private var mSortPresenter: SortList = SortList()
    var view = _view
    private var mSortType: String = "none"
    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()

    override fun getType(sortType: String) {
        mSortType = sortType
        sort()
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        _favoriteItem = list
        mFavoriteDataArray.clear()
        mFavoriteDataArray.addAll(_favoriteItem)
        sort()
    }

    override fun recieveBroadcast(context: Context) {
        mReceiveArray.clear()
        LocalBroadcastManager.getInstance(context).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    mReceiveArray = intent.extras?.getSerializable(RECEIVED_FAVORITE) as ArrayList<Mobiles>
                    view.showAllFavorite(mReceiveArray)
                    Log.d("receiveFav", mReceiveArray.toString())
                }
            },
            IntentFilter(RECEIVED_NEW_MESSAGE)
        )
    }

    override fun sendBroadcastMessage(mFavoriteArray: ArrayList<Mobiles>, context: Context) {
        Intent(RECEIVED_NEW_FAVORITE).let {
            it.putExtra(RECEIVED_NEW_FAVORITE_LIST, mFavoriteArray)
            LocalBroadcastManager.getInstance(context).sendBroadcast(it)
            Log.d("favArray", mFavoriteArray.toString())
        }

    }

    fun sort() {
        mReceiveFillterArray = mSortPresenter.sortMobileList(mFavoriteDataArray, mSortType)
        view.submitList(mReceiveFillterArray)
    }

}