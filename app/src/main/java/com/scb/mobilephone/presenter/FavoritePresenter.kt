//package com.scb.mobilephone.presenter
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.util.Log
//import androidx.localbroadcastmanager.content.LocalBroadcastManager
//import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE
//import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE_LIST
//import com.scb.mobilephone.model.ApiInterface
//import com.scb.mobilephone.model.Mobiles
//import com.scb.mobilephone.view.ListFragment
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class FavoritePresenter(_view: ListInterface.ListView) : ListInterface.ListPresenter {
//
//    private var view: ListInterface.ListView = _view
//    private var mReciveArray:ArrayList<Mobiles> = ArrayList()
//
//    override fun getMobileList(sortType: String) {
//
//        val call = ApiInterface.getClient().getMobileList()
//        call.enqueue(object : Callback<List<Mobiles>> {
//            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
//                Log.d("getApi", t.message.toString())
////                view.hideLoading()
//            }
//
//            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
//                Log.d("mobile-feed", response.toString())
//                if (response.isSuccessful) {
//                    view.showAllMobiles(response.body()!!)
//                    view.hideLoading()
//                    Log.d("mobile-feed", response.body().toString())
//                }
//
//            }
//
//        })
//
//    }
//
//    override fun recieveBroadcast(context: Context) {
//        mReciveArray.clear()
//        LocalBroadcastManager.getInstance(context).registerReceiver(
//            object : BroadcastReceiver() {
//                override fun onReceive(context: Context, intent: Intent) {
//                    mReciveArray = intent.extras?.getSerializable(RECEIVED_NEW_FAVORITE_LIST) as ArrayList<Mobiles>
//                    ListFragment.mobileListAdapter.reciveFavoriteList(mReciveArray)
//                    Log.d("reciveFav", mReciveArray.toString())
//                }
//            },
//            IntentFilter(RECEIVED_NEW_FAVORITE)
//        )
//
//
//    }
//
//}