package com.scb.mobilephone.fragment



import android.annotation.SuppressLint
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
        var mFillterArray: ArrayList<Mobiles> = ArrayList<Mobiles>()
        var mDataArray: ArrayList<Mobiles> = ArrayList<Mobiles>()
        @SuppressLint("StaticFieldLeak")
        lateinit var mAdapter: ListAdapter
    }
    private var mFeedType = "none"

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
        mAdapter = ListAdapter(context!!)
        view.recyclerViewList?.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(activity)
        }
        feedData(mFeedType )
        view.swipeRefresh.setOnRefreshListener {
            feedData(mFeedType)
        }
    }


    fun feedData(sortType: String) {
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
                    mFillterArray.clear()
                    mDataArray.addAll(response.body()!!)

                    when (sortType) {
                        "Price low to high" ->  {
                            mFillterArray.addAll(mDataArray.sortedBy { it.price })
                        }
                        "Price high to low" ->  {
                            mFillterArray.addAll( mDataArray.sortedByDescending { it.price })
                        }
                        "Rating 5-1" -> {
                            mFillterArray.addAll( mDataArray.sortedByDescending { it.rating })
                        }
                        else -> {
                            mFillterArray.addAll(mDataArray)
                        }
                    }
                    Log.d("sort", sortType)
                    Log.d("sort", mFillterArray.toString())
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

