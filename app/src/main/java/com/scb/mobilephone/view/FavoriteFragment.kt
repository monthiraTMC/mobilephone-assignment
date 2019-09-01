package com.scb.mobilephone.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE_LIST
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.presenter.FavoriteInterface
import com.scb.mobilephone.presenter.FavoritePresenter
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class FavoriteFragment : Fragment(), FavoriteInterface.FavoriteView {

    private lateinit var rvFavoriteList: RecyclerView
    private var mSortType = "none"

//    private var mReciveArray: ArrayList<Mobiles> = ArrayList()

    companion object {
        lateinit var mFavoriteAdapter: FavoriteAdapter
        lateinit var favoritePresenter: FavoriteInterface.FavoritePresenter

    }


    private var type = "none"
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

    //    fun getType(srortType: String) {
//        type = srortType
//        Log.d("fillter", type)
//        mFavoriteAdapter.sort(type)
//
//    }
    override fun getSortType(sortType: String) {
        mSortType = sortType
        favoritePresenter.getType(mSortType)
        mFavoriteAdapter.sort(mSortType)
        Log.d("favsort", mSortType)

    }


    inner class FavoriteAdapter : RecyclerView.Adapter<FavoriteHolder>(),
        CustomItemTouchHelperListener {

        private var _favoriteMobiles: ArrayList<Mobiles> = ArrayList()
        private var mFillterArray: ArrayList<Mobiles> = ArrayList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false)
            return FavoriteHolder(view)
        }

        override fun getItemCount(): Int {
            return mFillterArray.size
        }

        override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
            var item = mFillterArray[position]
            holder.mTitle.text = item.name
            holder.mPrice.text = item.price.toString()
            holder.mRating.text = "Rating: " + item.rating.toString()
            Glide.with(this@FavoriteFragment).load(item.thumbImageURL).into(holder.mImage)
            holder.itemView.setTag(R.id.view_pager, item.id)

        }


        override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
            Collections.swap(mFillterArray, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onItemDismiss(position: Int) {
            _favoriteMobiles?.removeAt(position)
            mFillterArray.removeAt(position)
            Log.d("remove", mFillterArray.toString())
            sendBroadcastMessage(mFillterArray)
            notifyItemRemoved(position)
        }

        fun submitList(list: ArrayList<Mobiles>) {
            _favoriteMobiles = list
            mFillterArray.clear()
            mFillterArray.addAll(_favoriteMobiles)
            notifyDataSetChanged()

        }


        fun sort(sortType: String) {
            mFillterArray.clear()
            when (sortType) {
                "Price low to high" -> {
                    mFillterArray.addAll(_favoriteMobiles.sortedBy { it.price })
                }
                "Price high to low" -> {
                    mFillterArray.addAll(_favoriteMobiles.sortedByDescending { it.price })
                }
                "Rating 5-1" -> {
                    mFillterArray.addAll(_favoriteMobiles.sortedByDescending { it.rating })
                }
                else -> {
                    mFillterArray.addAll(_favoriteMobiles)
                }
            }
            Log.d("sortType", sortType)
            Log.d("sortType", mFillterArray.toString())
            notifyDataSetChanged()


        }

        private fun sendBroadcastMessage(mFavoriteArray: ArrayList<Mobiles>) {
            Intent(RECEIVED_NEW_FAVORITE).let {
                it.putExtra(RECEIVED_NEW_FAVORITE_LIST, mFavoriteArray)
                LocalBroadcastManager.getInstance(context!!).sendBroadcast(it)
                Log.d("favorite", mFavoriteArray.toString())
            }
        }

    }


    inner class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mImage = view.imageView
        val mTitle = view.titleTextView
        val mPrice = view.pricetextView
        val mRating = view.ratingtextView

    }

    class CustomItemTouchHelperCallback(private var listener: CustomItemTouchHelperListener) :
        ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

            val dragFlags = 0
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewHolder?.let {
                listener.onItemDismiss(viewHolder.adapterPosition)
            }
        }

    }

    interface CustomItemTouchHelperListener {
        fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

        fun onItemDismiss(position: Int)
    }
}

