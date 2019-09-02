package com.scb.mobilephone.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.helper.CustomItemTouchHelperCallback
import com.scb.mobilephone.helper.CustomItemTouchHelperListener
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class FavoriteFragment : Fragment(), FavoriteInterface.FavoriteView {


    override fun submitList(list: ArrayList<Mobiles>) {
        mFavoriteArray = list
        Log.d("favArray", mFavoriteArray.toString())
        mFavoriteAdapter.notifyDataSetChanged()
    }

    private lateinit var rvFavoriteList: RecyclerView


    var mFavoriteArray: ArrayList<Mobiles> = ArrayList()

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
        mFavoriteAdapter = FavoriteAdapter()

        rvFavoriteList.let {
            it.adapter = mFavoriteAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator()
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        favoritePresenter = FavoritePresenter(this)
        favoritePresenter.recieveBroadcast(context!!)


        val callback = CustomItemTouchHelperCallback(mFavoriteAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteList)

    }

    override fun getSortType(sortType: String) {
        favoritePresenter.getType(sortType)
        favoritePresenter.getMobileList()

    }

    override fun showAllFavorite(mobileList: ArrayList<Mobiles>) {
        favoritePresenter.submitList(mobileList)
    }


    inner class FavoriteAdapter : RecyclerView.Adapter<FavoriteHolder>(), CustomItemTouchHelperListener {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false)
            return FavoriteHolder(view)
        }

        override fun getItemCount(): Int {
            return mFavoriteArray.size
        }

        override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
            var item = mFavoriteArray[position]
            holder.mTitle.text = item.name
            holder.mPrice.text = item.price.toString()
            holder.mRating.text = "Rating: " + item.rating.toString()
            Glide.with(this@FavoriteFragment).load(item.thumbImageURL).into(holder.mImage)
            holder.itemView.setTag(R.id.view_pager, item.id)
        }


        override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
            Collections.swap(mFavoriteArray, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onItemDismiss(position: Int) {
            mFavoriteArray.removeAt(position)
            Log.d("remove", mFavoriteArray.toString())
            favoritePresenter.sendBroadcastMessage(mFavoriteArray, context!!)
            notifyItemRemoved(position)
        }
    }


    inner class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mImage = view.imageView
        val mTitle = view.titleTextView
        val mPrice = view.pricetextView
        val mRating = view.ratingtextView

    }


}

