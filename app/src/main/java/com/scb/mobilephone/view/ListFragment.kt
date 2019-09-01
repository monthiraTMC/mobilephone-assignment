package com.scb.mobilephone.view

import android.annotation.SuppressLint
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
import com.scb.mobilephone.R
import com.scb.mobilephone.adapter.ListAdapter
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.presenter.ListInterface
import com.scb.mobilephone.presenter.ListPresenter
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), ListInterface.ListView {

    private lateinit var rvMobileList: RecyclerView
    private var mFillterArray: ArrayList<Mobiles> = ArrayList()

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mobileListAdapter: ListAdapter
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

        mobileListAdapter = ListAdapter(context!!)

        rvMobileList.let {
            it.adapter = mobileListAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        presenter = ListPresenter(this)
        presenter.getMobileList()
        presenter.recieveBroadcast(context!!)

        swipeRefresh.setOnRefreshListener {
            presenter.getMobileList()
        }
    }

    override fun showLoading() {
        swipeRefresh.setRefreshing(true)
    }

    override fun hideLoading() {
        swipeRefresh.setRefreshing(false)
    }

    override fun showAllMobiles(mobileList: List<Mobiles>) {
        mobileListAdapter.submitList(mobileList)
        Log.d("sortMMM", mFillterArray.toString())
    }

    //    override fun submitList() {
//        mobileListAdapter.notifyDataSetChanged()
//    }
    override fun getSortType(sortType: String) {
        presenter.getType(sortType)
        mobileListAdapter.getType(sortType)
        Log.d("sortMMM", sortType)
    }

}

