package com.scb.mobilephone.presenter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Adapter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.scb.mobilephone.adapter.ListAdapter
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE_LIST
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.view.ListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPresenter(_view: ListInterface.ListView) : ListInterface.ListPresenter {

    private var view: ListInterface.ListView = _view
    private var mSortType: String = "none"
    private var mReciveArray:ArrayList<Mobiles> = ArrayList()
    private var mFillterArray: ArrayList<Mobiles> = ArrayList()

//    private var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()
    private var _mobiles: List<Mobiles> = listOf()

    override fun getMobileList() {
        val call = ApiInterface.getClient().getMobileList()
        call.enqueue(object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                Log.d("getApi", t.message.toString())
            }
            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("mobile-feed", response.toString())
                if (response.isSuccessful) {
                    view.showAllMobiles(response.body()!!)
                    view.hideLoading()
                    Log.d("mobile-feed", response.body().toString())
                }

            }

        })

    }

    override fun recieveBroadcast(context: Context) {
        mReciveArray.clear()
        LocalBroadcastManager.getInstance(context).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    mReciveArray = intent.extras?.getSerializable(RECEIVED_NEW_FAVORITE_LIST) as ArrayList<Mobiles>
                    ListFragment.mobileListAdapter.reciveFavoriteList(mReciveArray)
                    Log.d("reciveFav", mReciveArray.toString())
                }
            },
            IntentFilter(RECEIVED_NEW_FAVORITE)
        )


    }

    override fun submitList(list: List<Mobiles>) {
        _mobiles = list
        mFillterArray.clear()
        mFillterArray.addAll(_mobiles)
        sortList()
        view.submitList(mFillterArray)


    }
    override fun getType(sortType:String) {
        mSortType = sortType
        Log.d("sortList", mSortType)
    }

    fun sortList() {
        mFillterArray.clear()
        when (mSortType) {
            "Price low to high" -> {
                mFillterArray.addAll(_mobiles.sortedBy { it.price })
            }
            "Price high to low" -> {
                mFillterArray.addAll(_mobiles.sortedByDescending { it.price })
            }
            "Rating 5-1" -> {
                mFillterArray.addAll(_mobiles.sortedByDescending { it.rating })
            }
            else -> {
                mFillterArray.addAll(_mobiles)
            }
        }
        Log.d("sortListM", mSortType)
        Log.d("sortListM", mFillterArray.toString())

    }
}