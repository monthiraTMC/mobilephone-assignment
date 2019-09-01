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
import androidx.recyclerview.widget.*
import com.scb.mobilephone.R
import com.scb.mobilephone.adapter.FavoriteAdapter
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.presenter.FavoriteInterface
import com.scb.mobilephone.presenter.FavoritePresenter

class FavoriteFragment : Fragment(), FavoriteInterface.FavoriteView {

    private lateinit var rvFavoriteList: RecyclerView
    private lateinit var favoritePresenter: FavoriteInterface.FavoritePresenter

//    private var mReciveArray: ArrayList<Mobiles> = ArrayList()

    companion object {
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

        rvFavoriteList.let {
            it.adapter = mFavoriteAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator()
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }
        favoritePresenter = FavoritePresenter(this)
        favoritePresenter.recieveBroadcast(context!!)
        getType(type)

        val callback = CustomItemTouchHelperCallback(mFavoriteAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteList)

    }

    fun getType(srortType: String) {
        type = srortType
        Log.d("fillter", type)
        mFavoriteAdapter.sort(type)

    }


}


class CustomItemTouchHelperCallback(private var listener: com.scb.mobilephone.adapter.CustomItemTouchHelperListener) :
    ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder?.let {
            listener.onItemDismiss(viewHolder.adapterPosition)
        }
    }


}