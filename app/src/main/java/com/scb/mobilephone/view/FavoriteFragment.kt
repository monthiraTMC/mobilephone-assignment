package com.scb.mobilephone.view


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
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
import com.scb.mobilephone.adapter.FavoriteAdapter
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.model.Mobiles


class FavoriteFragment : Fragment() {
    private lateinit var rvFavoriteList: RecyclerView


    private var mReciveArray: ArrayList<Mobiles> = ArrayList()
    private var mFillterArray: ArrayList<Mobiles> = ArrayList()
    companion object{
        lateinit var mFavoriteAdapter: FavoriteAdapter
    }
    private var type = "none"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_favorites, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavoriteList = view.findViewById(R.id.recyclerViewFavorite)
        mFavoriteAdapter = FavoriteAdapter(context!!)
        rvFavoriteList.adapter = mFavoriteAdapter
        rvFavoriteList.layoutManager = LinearLayoutManager(context)
        rvFavoriteList.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        rvFavoriteList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rvFavoriteList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))

        initBroadcast()
        getType(type)

    }

    fun getType(srortType: String){
        type = srortType
        Log.d("fillter", type)
        mFavoriteAdapter.sort(type)
    }


    fun initBroadcast() {
        mReciveArray.clear()
        try {
            LocalBroadcastManager.getInstance(context!!).registerReceiver(
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        mReciveArray = intent.extras?.getSerializable(RECEIVED_FAVORITE) as ArrayList<Mobiles>
                        mFavoriteAdapter.submitList(mReciveArray)

                    }
                },
                IntentFilter(RECEIVED_NEW_MESSAGE)
            )
        }
        catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
}

