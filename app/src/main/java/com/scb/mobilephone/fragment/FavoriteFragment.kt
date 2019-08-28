package com.scb.mobilephone.fragment


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
import androidx.recyclerview.widget.LinearLayoutManager
import com.scb.mobilephone.R
import com.scb.mobilephone.adapter.FavoriteAdapter
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.fragment_favorites.view.*


class FavoriteFragment : Fragment() {
    companion object{
        var mReciveArray: ArrayList<Mobiles> = ArrayList()
    }

    lateinit var mFavoriteAdapter: FavoriteAdapter
    //    private var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_favorites, container, false)
//        initBroadcast()
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFavoriteAdapter = FavoriteAdapter(context!!)
        view.recyclerViewFavorite?.let {
            it.adapter = mFavoriteAdapter
            it.layoutManager = LinearLayoutManager(activity)
        }
        initBroadcast()
    }

    private fun feedData() {
        initBroadcast()
        mFavoriteAdapter.notifyDataSetChanged()
        Log.d("recived", mReciveArray.toString())

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//    }


    private fun initBroadcast() {
        mReciveArray.clear()
        LocalBroadcastManager.getInstance(context!!).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    mReciveArray = intent.extras?.getSerializable(RECEIVED_FAVORITE) as ArrayList<Mobiles>
                    mFavoriteAdapter.notifyDataSetChanged()
                    Log.d("recived", mReciveArray.toString())
//                    txtView.setText(mReciveArray.toString())
                }
            },
            IntentFilter(RECEIVED_NEW_MESSAGE)
        )
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

    }


}
