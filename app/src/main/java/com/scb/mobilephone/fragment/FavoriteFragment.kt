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
import androidx.recyclerview.widget.*
import com.scb.mobilephone.R
import com.scb.mobilephone.adapter.FavoriteAdapter
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.fragment.FavoriteFragment.Companion.mFavoriteAdapter
import com.scb.mobilephone.helper.CustomItemTouchHelperCallback
import com.scb.mobilephone.helper.CustomItemTouchHelperListener
import com.scb.mobilephone.model.Mobiles


class FavoriteFragment : Fragment() {
    private lateinit var rvFavoriteList: RecyclerView

    private var mReciveArray: ArrayList<Mobiles> = ArrayList()
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
        rvFavoriteList.layoutManager = LinearLayoutManager(context!!)
        rvFavoriteList.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        rvFavoriteList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rvFavoriteList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))

        initBroadcast()
        getType(type)

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                mFavoriteAdapter.removeItem(viewHolder.adapterPosition, viewHolder)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteList)

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
        catch (e : NullPointerException) {
            e.printStackTrace()
        }
    }
}

