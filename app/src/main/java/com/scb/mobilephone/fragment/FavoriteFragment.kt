package com.scb.mobilephone.fragment


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
import com.scb.mobilephone.adapter.FavoriteAdapter
import com.scb.mobilephone.adapter.ListAdapter
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.network.ApiInterface
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteFragment : Fragment() {
    private lateinit var rvFavoriteList: RecyclerView


    private var mReciveArray: ArrayList<Mobiles> = ArrayList()
    lateinit var mFavoriteAdapter: FavoriteAdapter

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

    }


    private fun initBroadcast() {
        mReciveArray.clear()
        LocalBroadcastManager.getInstance(context!!).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    mReciveArray = intent.extras?.getSerializable(RECEIVED_FAVORITE) as ArrayList<Mobiles>
                    mFavoriteAdapter.submitList(mReciveArray)
                    Log.d("recived", mReciveArray.toString())
                }
            },
            IntentFilter(RECEIVED_NEW_MESSAGE)
        )
    }
}








//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mFavoriteAdapter = FavoriteAdapter(context!!)
//        view.recyclerViewFavorite?.let {
//            it.adapter = mFavoriteAdapter
//            it.layoutManager = LinearLayoutManager(activity)
//        }
//        initBroadcast()
//    }
//
//    private fun feedData() {
//        initBroadcast()
//        mFavoriteAdapter.notifyDataSetChanged()
//        Log.d("recived", mReciveArray.toString())
//
//    }
//
////    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
////
////
////    }
//
//
//    private fun initBroadcast() {
//        mReciveArray.clear()
//        LocalBroadcastManager.getInstance(context!!).registerReceiver(
//            object : BroadcastReceiver() {
//                override fun onReceive(context: Context, intent: Intent) {
//                    mReciveArray = intent.extras?.getSerializable(RECEIVED_FAVORITE) as ArrayList<Mobiles>
//                    mFavoriteAdapter.notifyDataSetChanged()
//                    Log.d("recived", mReciveArray.toString())
////                    txtView.setText(mReciveArray.toString())
//                }
//            },
//            IntentFilter(RECEIVED_NEW_MESSAGE)
//        )
//
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(
//            object : BroadcastReceiver() {
//                override fun onReceive(context: Context, intent: Intent) {
//                    newText.text = intent.getStringExtra(NEW_MESSAGE)
//                }
//            },
//            IntentFilter(RECEIVED_NEW_MESSAGE2)
//        )





