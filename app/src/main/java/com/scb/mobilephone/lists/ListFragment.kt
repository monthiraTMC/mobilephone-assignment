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
        this.mDataArray = list
        mobileListAdapter.notifyDataSetChanged()
    }

    private lateinit var rvMobileList: RecyclerView
    var mDataArray: ArrayList<Mobiles> = ArrayList()
    var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()

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
        mobileListAdapter = ListAdapter()

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
            presenter.getMobileList()
        }
        presenter.getMobileList()

    }

    override fun showLoading() {
        swipeRefreshLayout.setRefreshing(true)
    }

    override fun hideLoading() {
        swipeRefreshLayout.setRefreshing(false)
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
                    intent.putExtra(MOBILE_LIST, item)
                    startActivity(intent)
                }
            }

            holder.mFavoriteToggle.text = null
            holder.mFavoriteToggle.textOn = null
            holder.mFavoriteToggle.textOff = null

            holder.mFavoriteToggle.isChecked = item in mFavoriteDataArray

            holder.mFavoriteToggle.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                   if (item in mFavoriteDataArray) {

                   } else {
                       mFavoriteDataArray.add(item)
                       presenter.sendBroadcast(mFavoriteDataArray, context!!)
                   }

                } else {
                    mFavoriteDataArray.remove(item)
                }
                Log.d("favArrayListSend", mFavoriteDataArray.toString())
            }
        }
    }


    inner class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mImage = view.imageView
        val mTitle = view.titleTextView
        val mDescription = view.descriptionTextView
        val mPrice = view.pricetextView
        val mRating = view.ratingtextView
        val mFavoriteToggle = view.btnFavorite

    }

}

