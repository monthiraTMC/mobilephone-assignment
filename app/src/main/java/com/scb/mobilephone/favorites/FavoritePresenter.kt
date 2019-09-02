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
import com.scb.mobilephone.helper.SortPresenter
import kotlin.NullPointerException as NullPointerException1

class FavoritePresenter(_view: FavoriteInterface.FavoriteView) :
    FavoriteInterface.FavoritePresenter {

    private lateinit var  mReceiveFillterArray: ArrayList<Mobiles>
    var _favoriteItem: ArrayList<Mobiles> = ArrayList()
    var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()
    private var mSortPresenter: SortPresenter = SortPresenter()
    var view = _view
    private var mSortType: String = "none"
    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()

    override fun getType(sortType: String) {
        mSortType = sortType
    }

    override fun getMobileList() {
        view.showAllFavorite(mReceiveFillterArray)
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        _favoriteItem = list
        mFavoriteDataArray.clear()
        mFavoriteDataArray.addAll(_favoriteItem)
        mReceiveFillterArray = mSortPresenter.sortMobileList(mFavoriteDataArray, mSortType)
        view.submitList(mReceiveFillterArray)
    }

    override fun recieveBroadcast(context: Context) {
        mReceiveArray.clear()
        try {
            LocalBroadcastManager.getInstance(context!!).registerReceiver(
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        mReceiveArray = intent.extras?.getSerializable(RECEIVED_FAVORITE) as ArrayList<Mobiles>
                        Log.d("favArrayReceive", mReceiveArray.toString())
                        view.showAllFavorite(mReceiveArray)
                    }
                },
                IntentFilter(RECEIVED_NEW_MESSAGE)
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun sendBroadcastMessage(mFavoriteArray: ArrayList<Mobiles>, context: Context) {
        Intent(RECEIVED_NEW_FAVORITE).let {
            it.putExtra(RECEIVED_NEW_FAVORITE_LIST, mFavoriteArray)
            LocalBroadcastManager.getInstance(context).sendBroadcast(it)
            Log.d("favArray", mFavoriteArray.toString())
        }

    }

}