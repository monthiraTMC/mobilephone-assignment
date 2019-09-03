package com.scb.mobilephone.lists

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.scb.mobilephone.datails.DetailActivity
import com.scb.mobilephone.extensions.*
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.helper.SortList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPresenter(_view: ListInterface.ListView, _context: Context) :
    ListInterface.ListPresenter {

    override fun gotoDetailPage(item: Mobiles) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(MOBILE_LIST, item)
        context.startActivity(intent)
    }

    override fun addToMobileList(mobiles: ArrayList<Mobiles>) {
        mFillterArray = mobiles
        sort()
    }

    override fun getApiMobileList() {
        view.showLoading()
        mReceiveArray.clear()
        val call = ApiInterface.getClient().getMobileList()
        call.enqueue(object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                Log.d("getApi", t.message.toString())
                view.hideLoading()
                context.showToast("Cannot load api " + t.message.toString())
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("mobile-feed", response.toString())
                if (response.isSuccessful) {
                    context.showToast("Successfully")
                    mReceiveArray.addAll(response.body()!!)
                    Log.d("mobile-feed", mReceiveArray.toString())
                    view.showAllMobiles(mReceiveArray)
                    view.hideLoading()
                }

            }

        })

    }


    override fun submitList(list: List<Mobiles>) {
        _mobiles = list
        mFillterArray.clear()
        mFillterArray.addAll(_mobiles)
        sort()
    }

    override fun getType(sortType: String) {
        mSortType = sortType
        sort()
    }

    fun sort() {
        mRecieveFillterArray = mSortPresenter.sortMobileList(mFillterArray, mSortType)
        view.submitList(mRecieveFillterArray)
    }

    private var view: ListInterface.ListView = _view
    private var context = _context
    private var mSortType: String = "none"
    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()
    private var mFillterArray: ArrayList<Mobiles> = ArrayList()
    private var mRecieveFillterArray: ArrayList<Mobiles> = ArrayList()
    private var mSortPresenter: SortList = SortList()
    private var _mobiles: List<Mobiles> = listOf()


}