package com.scb.mobilephone.fragment


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scb.mobilephone.R
import com.scb.mobilephone.adapter.ListAdapter
import com.scb.mobilephone.model.Mobiles

import com.scb.mobilephone.network.ApiInterface
import kotlinx.android.synthetic.main.fragment_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListFragment : Fragment() {
    companion object{
        var mDataArray: ArrayList<Mobiles> = ArrayList<Mobiles>()
    }
    private lateinit var mAdapter: ListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_list, container, false)
        mAdapter = ListAdapter(context!!)
        _view.recyclerViewList.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(activity)
        }

        feedData()
        _view.swipeRefresh.setOnRefreshListener {
            feedData()
        }
        return _view
    }


    fun feedData() {
        //template connect to network
        val call = ApiInterface.getClient().getMobileList()
        //check request
        Log.d("SCB_NETWORK", call.request().url().toString())
        call.enqueue(object : Callback<List<Mobiles>>{
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                Log.d("SCB_NETWORK", t.message.toString())
                view?.swipeRefresh?.isRefreshing = false
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("SCB_NETWORK", response.body().toString())
                if (response.isSuccessful) {
                    mDataArray.clear()
                    mDataArray.addAll(response.body()!!)

                    //important
                    mAdapter.notifyDataSetChanged()

                    Handler().postDelayed({
                        view?.swipeRefresh?.isRefreshing = false
                    }, 2000)
                }

            }

        })
    }


}

