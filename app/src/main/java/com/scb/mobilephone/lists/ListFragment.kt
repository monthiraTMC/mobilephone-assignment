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
import com.scb.mobilephone.database.DatabaseInterface
import com.scb.mobilephone.database.DatabasePresenter
import com.scb.mobilephone.extensions.THREAD_NAME
import com.scb.mobilephone.helper.*
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), ListInterface.ListView, SortInterface, UpdateInterface,
    DatabaseInterface.DatabaseToView {
    override fun showUpdateFavoriteList(list: ArrayList<Mobiles>) {
        this.mFavoriteArray = list
        mobileListAdapter.mFavoriteArray = list
        rvMobileList.post {
            mobileListAdapter.notifyDataSetChanged()
        }
    }

    override fun getUpdateFavorite(): ArrayList<Mobiles> {
        return this.mFavoriteArray
    }

    override fun updateToFavorite(list: ArrayList<Mobiles>) {
        this.showUpdateFavoriteList(list)
    }

    override fun showAllMobiles(mobiles: ArrayList<Mobiles>) {
        mDataArray = mobiles
        listPresenter.addToMobileList(mobiles)
    }

    override fun getSortType(sortType: String) {
        listPresenter.getSortType(sortType)
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        mobileListAdapter.mMobileArray.clear()
        mobileListAdapter.mMobileArray.addAll(list)
        mobileListAdapter.notifyDataSetChanged()
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
        mThread = CMWorkerThread(THREAD_NAME).also { it.start() }
        databasePresenter = DatabasePresenter(this, context!!, mThread)
        mobileListAdapter = ListAdapter(context!!, object : ListAdapter.MobileListListener {
            override fun gotoDetailPage(item: Mobiles) {
                listPresenter.gotoDetailPage(item)
            }

            override fun addToFavorite(item: Mobiles) {
                databasePresenter.addToFavorite(item)
            }

            override fun removeFavorite(item: Mobiles) {
                databasePresenter.removeFavorite(item)
            }

        })

        rvMobileList.let {
            it.adapter = mobileListAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        sortPresenter = SortList(this)
        listPresenter = ListPresenter(this, context!!, mThread, object : ListPresenter.SortListener {
            override fun getSortList(sortType: String, mobiles: ArrayList<Mobiles>) {
                sortPresenter.sortMobileList(sortType, mobiles)
            }

        })

        databasePresenter.setupDatabase()
        databasePresenter.getAllFavorite()
        listPresenter.getApiMobileList()

        swipeRefresh.setOnRefreshListener {
            databasePresenter.setupDatabase()
            listPresenter.getApiMobileList()
        }
    }

    override fun showLoading() {
        swipeRefreshLayout.setRefreshing(true)
    }

    override fun hideLoading() {
        swipeRefreshLayout.setRefreshing(false)
    }

    private lateinit var rvMobileList: RecyclerView
    private var mDataArray: ArrayList<Mobiles> = ArrayList()
    private var mFavoriteArray: ArrayList<Mobiles> = ArrayList()
    private lateinit var mobileListAdapter: ListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mThread: CMWorkerThread
    lateinit var listPresenter: ListInterface.ListPresenter
    private lateinit var sortPresenter: SortPresenter
    private lateinit var databasePresenter: DatabaseInterface.DatabasePresenter

}

