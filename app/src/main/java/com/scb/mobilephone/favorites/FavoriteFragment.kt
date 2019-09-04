package com.scb.mobilephone.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.scb.mobilephone.R
import com.scb.mobilephone.helper.CustomItemTouchHelperCallback
import com.scb.mobilephone.main.MainInterface
import com.scb.mobilephone.main.MainPresenter
import com.scb.mobilephone.model.Mobiles

class FavoriteFragment : Fragment(), FavoriteInterface.FavoriteView, MainPresenter.ViewListener{
    override fun getSortType(sortType: String) {
        Log.d("favArray", sortType.toString())    }

    override fun submitList(list: ArrayList<Mobiles>) {
        mFavoriteAdapter.mFavoriteArray = list
        Log.d("favArray", list.toString())
        mFavoriteAdapter.notifyDataSetChanged()
    }

    private lateinit var rvFavoriteList: RecyclerView
    companion object {
        lateinit var mFavoriteAdapter: FavoriteAdapter
        lateinit var favoritePresenter: FavoriteInterface.FavoritePresenter
    }
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

        val callback = CustomItemTouchHelperCallback(mFavoriteAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteList)
    }

//    override fun getSortType(sortType: String) {
//        Log.d("getSortType", sortType)
//        favoritePresenter.getType(sortType)
//
//    }

    override fun showAllFavorite(mobileList: ArrayList<Mobiles>) {
        favoritePresenter.submitList(mobileList)
    }



}

