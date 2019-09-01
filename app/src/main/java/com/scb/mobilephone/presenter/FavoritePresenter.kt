package com.scb.mobilephone.presenter

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
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.view.FavoriteFragment
import com.scb.mobilephone.view.ListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritePresenter(_view: FavoriteInterface.FavoriteView) : FavoriteInterface.FavoritePresenter {
    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()
    override fun recieveBroadcast(context: Context) {
        mReceiveArray.clear()
        try {
            LocalBroadcastManager.getInstance(context!!).registerReceiver(
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        mReceiveArray = intent.extras?.getSerializable(RECEIVED_FAVORITE) as ArrayList<Mobiles>
                        FavoriteFragment.mFavoriteAdapter.submitList(mReceiveArray)

                    }
                },
                IntentFilter(RECEIVED_NEW_MESSAGE)
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


}