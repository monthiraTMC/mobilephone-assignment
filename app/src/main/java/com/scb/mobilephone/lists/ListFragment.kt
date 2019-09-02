package com.scb.mobilephone.lists

import android.content.Intent
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
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.datails.DetailActivity
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_list.view.*


class ListFragment : Fragment(), ListInterface.ListView {

    override fun submitList(list: ArrayList<Mobiles>) {
        mobileListAdapter.mMobileArray = list
        mobileListAdapter.notifyDataSetChanged()
    }

    private lateinit var rvMobileList: RecyclerView
    private lateinit var mobileListAdapter: ListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    companion object {
        lateinit var presenter: ListInterface.ListPresenter
    }


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
        mobileListAdapter = ListAdapter(context!!)

        rvMobileList.let {
            it.adapter = mobileListAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        presenter = ListPresenter(this, context!!)
        presenter.recieveBroadcast(context!!)
        swipeRefresh.setOnRefreshListener {
            presenter.getApiMobileList()
        }
        presenter.getApiMobileList()

    }

    override fun showLoading() {
        swipeRefreshLayout.setRefreshing(true)
    }

    override fun hideLoading() {
        swipeRefreshLayout.setRefreshing(false)
    }

    override fun showAllMobiles(mobileList: ArrayList<Mobiles>) {
        presenter.submitList(mobileList)
        presenter.addToMobileList(mobileList)

    }

    override fun getSortType(sortType: String) {
        presenter.getType(sortType)

    }

    override fun reciveFavoriteList(favoriteList: ArrayList<Mobiles>) {
        mobileListAdapter.mFavoriteArray = favoriteList
        mobileListAdapter.notifyDataSetChanged()
    }


}

