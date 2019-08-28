package com.scb.mobilephone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.fragment.FavoriteFragment.Companion.mReciveArray
import kotlinx.android.synthetic.main.item_list.view.*

class FavoriteAdapter(val context: Context) : RecyclerView.Adapter<FavoriteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteHolder(view)
    }

    override fun getItemCount(): Int {
        return mReciveArray.size
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        var item = mReciveArray[position]
        holder.mTitle.text = item.name
        holder.mPrice.text = item.price.toString()
        holder.mRating.text = "Rating: " + item.rating.toString()
        Glide.with(context).load(item.thumbImageURL).into(holder.mImage)
        holder.itemView.setTag(R.id.view_pager, item.id)

    }

}


class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mImage = view.imageView
    val mTitle = view.titleTextView
    val mPrice = view.pricetextView
    val mRating = view.ratingtextView

}
