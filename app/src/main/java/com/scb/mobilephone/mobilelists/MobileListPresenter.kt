package com.scb.mobilephone.mobilelists

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.database.DatabaseInterface
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.helper.SortInterface
import com.scb.mobilephone.helper.SortList
import com.scb.mobilephone.mobiledetails.DetailActivity
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MobileListPresenter(private val view: MobileListInterface.MobileListView,
                          private val sortPresenter: SortInterface.SortPresenter) :
    MobileListInterface.MobileListPresenter {
    override fun showAllMobile(list: ArrayList<Mobiles>) {
        this.mMobileArray = list
        sortPresenter.sortMobileList(mSortType, list)
    }

    override fun getFavorite(list: ArrayList<Mobiles>) {
        view.notifyFavoriteChange(list)
    }

    override fun getSortType(sortType: String) {
        this.mSortType = sortType
        sortPresenter.sortMobileList(mSortType, mMobileArray)
    }

    override fun getApiMobileList() {
        view.showLoading()
        mReceiveArray.clear()
        val call = ApiInterface.getClient().getMobileList()
        call.enqueue(
            object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                Log.d("getApi", t.message.toString())
                view.hideLoading()
                view.showDialog()
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("mobile-feed", response.toString())
                if (response.isSuccessful) {
                    view.hideLoading()
                    view.showToastMessage("Successfully")
                    mReceiveArray.addAll(response.body()!!)
                    Log.d("mobile-feed", mReceiveArray.toString())
                    view.getAllMobiles(mReceiveArray)
                }

            }

        })

    }

    private var mSortType = "none"
    private var mMobileArray: ArrayList<Mobiles> = ArrayList()
    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()

}