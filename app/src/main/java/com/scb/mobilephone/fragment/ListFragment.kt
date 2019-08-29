package com.scb.mobilephone.fragment


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephone.R
import com.scb.mobilephone.adapter.ListAdapter
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE_LIST
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.network.ApiInterface
import kotlinx.android.synthetic.main.fragment_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListFragment : Fragment() {

    private lateinit var rvMobileList: RecyclerView
    private var mReciveArray: ArrayList<Mobiles> = ArrayList()
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var mobileListAdapter: ListAdapter
    }
    private var mType = "none"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_list, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobileList = view.findViewById(R.id.recyclerViewList)
        mobileListAdapter = ListAdapter(context!!)
        rvMobileList.adapter = mobileListAdapter
        rvMobileList.layoutManager = LinearLayoutManager(context)
        rvMobileList.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        rvMobileList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rvMobileList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))

        loadMobileList(mType)
        view.swipeRefresh.setOnRefreshListener {
            loadMobileList(mType)
        }
        reciveBroadcast()

    }


    fun loadMobileList(sortType: String) {
        mType = sortType
        val call = ApiInterface.getClient().getMobileList()
        call.enqueue(object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                context?.showToast("Can not call mobile list $t")
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("mobile-feed", response.toString())
                if (response.isSuccessful) {
                    val mobileList = response.body() ?: return
                    mobileListAdapter.submitList(mobileList, sortType)
                    Handler().postDelayed({
                        view?.swipeRefresh?.isRefreshing = false
                    }, 2000)
                    Log.d("mobile-feed", response.body().toString())
                }

            }

        })
    }

    fun reciveBroadcast() {
        mReciveArray.clear()

        LocalBroadcastManager.getInstance(context!!).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    mReciveArray = intent.extras?.getSerializable(RECEIVED_NEW_FAVORITE_LIST) as ArrayList<Mobiles>
                    mobileListAdapter.reciveFavoriteList(mReciveArray)
                    Log.d("reciveFav", mReciveArray.toString())


                }
            },
            IntentFilter(RECEIVED_NEW_FAVORITE)
        )

    }

}
