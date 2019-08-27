package com.scb.mobilephone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.fragment.ListFragment.Companion.mDataArray
import kotlinx.android.synthetic.main.item_list.view.*


class ListAdapter(val context: Context) : RecyclerView.Adapter<ListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return ListHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataArray.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val item = mDataArray[position]
        holder.mTitle.text = item.name
        holder.mDescription.text = item.description
        holder.mPrice.text = "Price: " + item.price.toString()
        holder.mRating.text = "Rating:" + item.rating.toString()
        Glide.with(context).load(item.thumbImageURL).into(holder.mImage)
        holder.itemView.setTag(R.id.view_pager, item.id)
    }
}


class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mImage = view.imageView
    val mTitle = view.titleTextView
    val mDescription = view.descriptionTextView
    val mPrice = view.pricetextView
    val mRating = view.ratingtextView
}
