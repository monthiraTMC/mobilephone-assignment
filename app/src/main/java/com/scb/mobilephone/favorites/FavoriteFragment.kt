package com.scb.mobilephone.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.THREAD_NAME
import com.scb.mobilephone.helper.*
import com.scb.mobilephone.lists.ListInterface
import com.scb.mobilephone.lists.ListPresenter
import com.scb.mobilephone.main.MainInterface
import com.scb.mobilephone.main.MainPresenter
import com.scb.mobilephone.model.Mobiles

class FavoriteFragment : Fragment(), FavoriteInterface.FavoriteView, SortInterface {

    override fun getAllFavorite(list: ArrayList<Mobiles>) {
        mFavoriteAdapter.mFavoriteArray = list
        mFavoriteAdapter.notifyDataSetChanged()
    }

    override fun getSortType(sortType: String) {
        favoritePresenter.getType(sortType)
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        mFavoriteAdapter.mFavoriteArray = list
        mFavoriteAdapter.notifyDataSetChanged()
    }

    private lateinit var rvFavoriteList: RecyclerView
    private lateinit var mThread: CMWorkerThread
    private lateinit var sortPresenter: SortPresenter
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
        mFavoriteAdapter = FavoriteAdapter(context!!, object : FavoriteAdapter.FavoriteListener{
            override fun removeFavorite(item: Mobiles) {
                favoritePresenter.removeFavorite(item)
            }

        })
        rvFavoriteList.let {
            it.adapter = mFavoriteAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator()
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }
        mThread = CMWorkerThread(THREAD_NAME).also { it.start() }
        sortPresenter = SortFavoriteList(this)
        favoritePresenter = FavoritePresenter(this, context!!, mThread, object : FavoritePresenter.SortListener{
            override fun getSortList(sortType: String, mobiles: ArrayList<Mobiles>) {
                sortPresenter.sortMobileList(sortType, mobiles)
            }

        })

        favoritePresenter.setupDatabase()
        favoritePresenter.getAllFavorite()


        val callback = CustomItemTouchHelperCallback(mFavoriteAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteList)
    }



}

