package com.scb.mobilephone.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_FAVORITE_LIST
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class FavoriteAdapter(val context: Context) : RecyclerView.Adapter<FavoriteHolder>(), CustomItemTouchHelperListener {

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
        Glide.with(context).load(item.thumbImageURL).into(holder.mImage)
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


    fun sort(sortType:String) {
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
            LocalBroadcastManager.getInstance(context).sendBroadcast(it)
            Log.d("favorite", mFavoriteArray.toString())
        }
    }

}


class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mImage = view.imageView
    val mTitle = view.titleTextView
    val mPrice = view.pricetextView
    val mRating = view.ratingtextView

}


interface CustomItemTouchHelperListener {
    fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean

    fun onItemDismiss(position: Int)
}

