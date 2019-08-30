package com.scb.mobilephone.presenter

import android.os.Handler
import android.util.Log
import android.view.View
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.view.ListFragment
import kotlinx.android.synthetic.main.fragment_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPresenter(_view: ListInterface.ListView) : ListInterface.ListPresenter {
    private var view: ListInterface.ListView = _view

    override fun getMobileList(sortType: String) {

        val call = ApiInterface.getClient().getMobileList()
        call.enqueue(object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                Log.d("getApi", t.message.toString())
//                view.hideLoading()
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("mobile-feed", response.toString())
                if (response.isSuccessful) {
                    view.showAllMobiles(response.body()!!, sortType)
                    view.hideLoading()

//                    val mobileList = response.body() ?: return
//                    ListFragment.mobileListAdapter.submitList(mobileList, sortType)

                    Log.d("mobile-feed", response.body().toString())
                }

            }

        })

    }

}