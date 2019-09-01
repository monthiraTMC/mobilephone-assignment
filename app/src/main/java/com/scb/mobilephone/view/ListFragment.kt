package com.scb.mobilephone.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.presenter.ListInterface
import com.scb.mobilephone.presenter.ListPresenter
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_list.view.*


class ListFragment : Fragment(), ListInterface.ListView {

    override fun submitList(list: ArrayList<Mobiles>) {
        this.mDataArray = list
        mobileListAdapter.notifyDataSetChanged()
    }

    private lateinit var rvMobileList: RecyclerView
    var mDataArray: ArrayList<Mobiles> = ArrayList()
    var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()

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

        mobileListAdapter = ListAdapter()

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
        swipeRefresh?.setRefreshing(true)
    }

    override fun hideLoading() {
        swipeRefresh?.setRefreshing(false)
    }

    override fun showAllMobiles(mobileList: List<Mobiles>) {
        presenter.submitList(mobileList)
    }

    override fun getSortType(sortType: String) {
        presenter.getMobileList()
        presenter.getType(sortType)

    }

    override fun reciveFavoriteList(favoriteList: ArrayList<Mobiles>) {
        mFavoriteDataArray = favoriteList
        mobileListAdapter.notifyDataSetChanged()
    }

    inner class ListAdapter : RecyclerView.Adapter<ListHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
            return ListHolder(view)
        }

        override fun getItemCount(): Int {
            return mDataArray.size
        }

        override fun onBindViewHolder(holder: ListHolder, position: Int) {

            var item = mDataArray[position]
            var id = 0
            holder.mTitle.text = item.name
            holder.mDescription.text = item.description
            holder.mPrice.text = "Price: " + item.price.toString()
            holder.mRating.text = "Rating: " + item.rating.toString()
            Glide.with(this@ListFragment).load(item.thumbImageURL).into(holder.mImage)
            holder.itemView.setTag(R.id.view_pager, item.id)

            holder.itemView.setOnClickListener {
                val item_positon = holder.adapterPosition
                if (item_positon != RecyclerView.NO_POSITION) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(MOBILE_LIST, mDataArray[position])
                    startActivity(intent)
                }
            }

            holder.mFavoriteToggle.text = null
            holder.mFavoriteToggle.textOn = null
            holder.mFavoriteToggle.textOff = null

            holder.mFavoriteToggle.isChecked = item in mFavoriteDataArray

            holder.mFavoriteToggle.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    mFavoriteDataArray.add(item)
                    presenter.sendBroadcast(mFavoriteDataArray, context!!)
                } else {
                    mFavoriteDataArray.remove(item)
                    presenter.sendBroadcast(mFavoriteDataArray, context!!)
                }
            }
        }
    }


    inner class ListHolder(view: View): RecyclerView.ViewHolder(view) {
        val mImage = view.imageView
        val mTitle = view.titleTextView
        val mDescription = view.descriptionTextView
        val mPrice = view.pricetextView
        val mRating = view.ratingtextView
        val mFavoriteToggle = view.btnFavorite

    }

}

