package com.scb.mobilephone.favorites

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.favorites.FavoriteFragment.Companion.favoritePresenter
import com.scb.mobilephone.helper.CustomItemTouchHelperListener
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.item_favorite.view.*
import java.util.*
import kotlin.collections.ArrayList

class FavoriteAdapter(val context: Context, private val listener: FavoriteListener) :
    RecyclerView.Adapter<FavoriteHolder>(), CustomItemTouchHelperListener {

    var mFavoriteArray: ArrayList<Mobiles> = ArrayList()
    private var delete_position = 100

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
        Glide.with(context).load(item.thumbImageURL).into(holder.mImage)
        holder.itemView.setTag(R.id.view_pager, item.id)
        Log.d("UpdateFavorite02-d", delete_position.toString())
        Log.d("UpdateFavorite02", mFavoriteArray.size.toString())
        if(delete_position != 100) {
            listener.removeFavorite(item)
            delete_position = 100
        }

    }


    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(mFavoriteArray, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        listener.removeFavorite(mFavoriteArray[position])
        mFavoriteArray.removeAt(position)
        listener.updateFavorite(mFavoriteArray)
        Log.d("remove", position.toString())
        notifyItemRemoved(position)
    }

    interface FavoriteListener {
        fun updateFavorite(list: ArrayList<Mobiles>)
        fun removeFavorite(item : Mobiles)
    }
}


class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mImage = view.imageView
    val mTitle = view.titleTextView
    val mPrice = view.pricetextView
    val mRating = view.ratingtextView

}