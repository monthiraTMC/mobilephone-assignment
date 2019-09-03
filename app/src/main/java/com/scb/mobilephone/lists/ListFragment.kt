package com.scb.mobilephone.lists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.scb.mobilephone.R
import com.scb.mobilephone.favorites.FavoriteAdapter
import com.scb.mobilephone.favorites.FavoriteFragment
import com.scb.mobilephone.favorites.FavoriteInterface
import com.scb.mobilephone.favorites.FavoritePresenter
import com.scb.mobilephone.helper.AddFavorite
import com.scb.mobilephone.helper.FavoriteDataArrayInterface
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), ListInterface.ListView, FavoriteDataArrayInterface.PresentToListView {
    override fun getFavorite(list: ArrayList<Mobiles>) {
        Log.d("meen", list.toString())
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        mobileListAdapter.mMobileArray = list
        mobileListAdapter.notifyDataSetChanged()
    }

    private lateinit var rvMobileList: RecyclerView
    private lateinit var mobileListAdapter: ListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var mFavoriteFragment: FavoriteFragment = FavoriteFragment()

    companion object {
        lateinit var listPresenter: ListInterface.ListPresenter
    }
    private lateinit var addFavoritePresenter: FavoriteDataArrayInterface.GetFavoritePresenter

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
        rvMobileList = view.findViewById(R.id.recyclerViewList)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        listPresenter = ListPresenter(this, context!!)
        mobileListAdapter = ListAdapter(context!!, object : ListAdapter.MobileListListener{
            override fun gotoDetailPage(item: Mobiles) {
                listPresenter.gotoDetailPage(item)
            }

            override fun addToFavorite(item: Mobiles) {
                addFavoritePresenter.addToFavorite(item)
            }

            override fun removeFavorite(item: Mobiles) {
                addFavoritePresenter.removeFavorite(item)
            }

        })

        rvMobileList.let {
            it.adapter = mobileListAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }
        addFavoritePresenter = AddFavorite(this, mFavoriteFragment)


        listPresenter.recieveBroadcast(context!!)
        swipeRefresh.setOnRefreshListener {
            listPresenter.getApiMobileList()
        }
        listPresenter.getApiMobileList()
    }

    override fun showLoading() { swipeRefreshLayout.setRefreshing(true) }

    override fun hideLoading() { swipeRefreshLayout.setRefreshing(false) }

    override fun showAllMobiles(mobileList: ArrayList<Mobiles>) {
        listPresenter.submitList(mobileList)
        listPresenter.addToMobileList(mobileList)
    }

    override fun getSortType(sortType: String) {
        listPresenter.getType(sortType)
    }

    override fun reciveFavoriteList(favoriteList: ArrayList<Mobiles>) {
        mobileListAdapter.mFavoriteArray = favoriteList
        mobileListAdapter.notifyDataSetChanged()
    }


}

