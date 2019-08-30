package com.scb.mobilephone.view


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.scb.mobilephone.R
import com.scb.mobilephone.adapter.ListAdapter
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.presenter.ListInterface
import com.scb.mobilephone.presenter.ListPresenter
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListFragment : Fragment(), ListInterface.ListView {
    override fun showLoading() {
        swipeRefresh.setRefreshing(true)
    }

    override fun hideLoading() {
        swipeRefresh.setRefreshing(false)    }

    override fun showAllMobiles(mobileList: List<Mobiles>, sortType:String) {
        mobileListAdapter.submitList(mobileList, sortType)
    }


    private lateinit var rvMobileList: RecyclerView

    private lateinit var viewToInterface:ListInterface.ListView

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var mobileListAdapter: ListAdapter
        lateinit var presenter: ListInterface.ListPresenter
    }
    private var mType = "none"

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

        rvMobileList.let{
            it.adapter = mobileListAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        presenter = ListPresenter(this)
        presenter.getMobileList(mType)


        swipeRefresh.setOnRefreshListener {
            presenter.getMobileList(mType)
        }
        }

    }

