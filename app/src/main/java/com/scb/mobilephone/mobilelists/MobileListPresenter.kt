package com.scb.mobilephone.mobilelists

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.database.DatabaseInterface
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.mobiledetails.DetailActivity
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MobileListPresenter(private val view: MobileListInterface.MobileListView,
                          private val listener: DatabaseInterface.DatabaseListener) :
    MobileListInterface.MobileListPresenter {

    override fun getApiMobileList() {
        view.showLoading()
        mReceiveArray.clear()
        val call = ApiInterface.getClient().getMobileList()
        call.enqueue(
            object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                Log.d("getApi", t.message.toString())
                view.hideLoading()
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("mobile-feed", response.toString())
                if (response.isSuccessful) {
                    view.hideLoading()
                    listener.showToastMessage("Successfully")
                    mReceiveArray.addAll(response.body()!!)
                    Log.d("mobile-feed", mReceiveArray.toString())
                    view.showAllMobiles(mReceiveArray)
                }

            }

        })

    }

    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()

}