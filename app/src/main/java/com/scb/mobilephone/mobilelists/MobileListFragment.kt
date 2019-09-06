package com.scb.mobilephone.mobilelists

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.scb.mobilephone.R
import com.scb.mobilephone.database.DatabaseInterface
import com.scb.mobilephone.database.DatabasePresenter
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.THREAD_NAME
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.helper.CMWorkerThread
import com.scb.mobilephone.helper.SortInterface
import com.scb.mobilephone.helper.SortList
import com.scb.mobilephone.mobiledetails.DetailActivity
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.fragment_mobilelist.*


class MobileListFragment : Fragment(), MobileListInterface.MobileListView,
    SortInterface.SortToView, DatabaseInterface.DatabaseListener{

    override fun notifyFavoriteChange(list: ArrayList<Mobiles>) {
        mobileListAdapter.mFavoriteArray = list
        rvMobileList.post {
            mobileListAdapter.notifyDataSetChanged()
        }
    }

    override fun showDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.let {
            it.setTitle("Error")
            it.setMessage(" Cannot call API, Try again?")
            it.setPositiveButton("YES"){_, _->
                mobileListPresenter.getApiMobileList()
            }
            it.setNegativeButton("NO"){_, _->
                activity?.finishAffinity()
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun showToastMessage(message: String) {
        context?.showToast(message)
    }

    override fun updateFavorite() {
        databasePresenter.getAllFavorite()
    }

    override fun getDBFavorite(list: ArrayList<Mobiles>) {
        mobileListPresenter.getFavorite(list)
    }

    override fun getAllMobiles(mobiles: ArrayList<Mobiles>) {
        mobileListPresenter.showAllMobile(mobiles)
    }

    override fun getSortType(sortType: String) {
        mobileListPresenter.getSortType(sortType)
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        mobileListAdapter.mMobileArray = list
        mobileListAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mobilelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobileList = view.findViewById(R.id.recyclerViewList)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        mThread = CMWorkerThread(THREAD_NAME).also { it.start() }
        databasePresenter = DatabasePresenter(this, context!!, mThread)
        mobileListAdapter = ListAdapter(context!!, object : ListAdapter.MobileListListener {
            override fun gotoDetailPage(item: Mobiles) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(MOBILE_LIST, item)
                startActivity(intent)
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
            it.itemAnimator = DefaultItemAnimator()
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        sortPresenter = SortList(this)
        mobileListPresenter = MobileListPresenter(this, sortPresenter)
        databasePresenter.setupDatabase()
        databasePresenter.getAllFavorite()
        mobileListPresenter.getApiMobileList()
        swipeRefresh.setOnRefreshListener {
            databasePresenter.setupDatabase()
            databasePresenter.getAllFavorite()
            mobileListPresenter.getApiMobileList()
        }
    }

    override fun showLoading() {
        swipeRefreshLayout.setRefreshing(true)
    }

    override fun hideLoading() {
        swipeRefreshLayout.setRefreshing(false)
    }

    private lateinit var rvMobileList: RecyclerView
    private lateinit var mobileListAdapter: ListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mThread: CMWorkerThread
    private lateinit var mobileListPresenter: MobileListInterface.MobileListPresenter
    private lateinit var sortPresenter: SortInterface.SortPresenter
    private lateinit var databasePresenter: DatabaseInterface.DatabasePresenter

}

