package com.scb.mobilephone.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.helper.*
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class FavoriteFragment : Fragment(), FavoriteInterface.FavoriteView, FavoriteDataArrayInterface.PresentToFavoriteView {
    override fun getFavorite(list: ArrayList<Mobiles>) {
        Log.d("meen2", list.toString())
    }


    override fun submitList(list: ArrayList<Mobiles>) {
        mFavoriteAdapter.mFavoriteArray = list
        Log.d("favArray", list.toString())
        mFavoriteAdapter.notifyDataSetChanged()
    }

//    private lateinit var dataPresenter: FavoriteDataArray
    private lateinit var rvFavoriteList: RecyclerView

    companion object {
        lateinit var mFavoriteAdapter: FavoriteAdapter
        lateinit var favoritePresenter: FavoriteInterface.FavoritePresenter
    }
    lateinit var getFavoritePresenter: FavoriteDataArrayInterface.GetFavoritePresenter
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


        val callback = CustomItemTouchHelperCallback(mFavoriteAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteList)
    }

    override fun getSortType(sortType: String) {
        Log.d("getSortType", sortType)
        favoritePresenter.getType(sortType)

    }

    override fun showAllFavorite(mobileList: ArrayList<Mobiles>) {
        favoritePresenter.submitList(mobileList)
    }



}

