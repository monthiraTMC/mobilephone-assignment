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


class ListFragment : Fragment(), ListInterface.ListView, SortInterface, DatabaseInterface.DatabaseListener {
    override fun getDBFavorite(list: ArrayList<Mobiles>) {
        this.mFavoriteArray = list
    }

    override fun showAllMobiles(mobiles: ArrayList<Mobiles>) {
        mMobileArray = mobiles
        sortPresenter.sortMobileList(mSortType, mMobileArray)
    }

    override fun getSortType(sortType: String) {
        mSortType = sortType
        sortPresenter.sortMobileList(mSortType, mMobileArray)
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        mobileListAdapter.mFavoriteArray = mFavoriteArray
        mobileListAdapter.mMobileArray = list
        mobileListAdapter.notifyDataSetChanged()
    }

    private lateinit var rvMobileList: RecyclerView
    private var mMobileArray: ArrayList<Mobiles> = ArrayList()
    private var mFavoriteArray: ArrayList<Mobiles> = ArrayList()
    private lateinit var mobileListAdapter: ListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mThread: CMWorkerThread
    private lateinit var listPresenter: ListInterface.ListPresenter


    private lateinit var sortPresenter: SortPresenter
    private lateinit var databasePresenter: DatabaseInterface.DatabasePresenter
    private var mSortType = "none"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        listPresenter = ListPresenter(this, context!!)

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

}

