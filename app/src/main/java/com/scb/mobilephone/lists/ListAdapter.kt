package com.scb.mobilephone.lists

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(val context: Context, private val listener: MobileListListener): RecyclerView.Adapter<ListHolder>() {
    var mMobileArray: ArrayList<Mobiles> = ArrayList()
    var mFavoriteArray: List<Mobiles> = listOf()
    var click_fav = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return ListHolder(view)
    }

    override fun getItemCount(): Int {
        return mMobileArray.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        var item = mMobileArray[position]
        holder.mTitle.text = item.name
        holder.mDescription.text = item.description
        holder.mPrice.text = "Price: $" + item.price.toString()
        holder.mRating.text = "Rating: " + item.rating.toString()
        Glide.with(context).load(item.thumbImageURL).into(holder.mImage)
        holder.itemView.setTag(R.id.view_pager, item.id)
        holder.itemView.setOnClickListener { listener.gotoDetailPage(item) }

        if (item in mFavoriteArray){
            holder.mBtnFavorite.setImageResource(R.drawable.ic_favorite_fillcolor)
            click_fav = true
        }
        else {
            holder.mBtnFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            click_fav = false
        }

        holder.mBtnFavorite.setOnClickListener {
            if (click_fav == false){
                holder.mBtnFavorite.setImageResource(R.drawable.ic_favorite_fillcolor)
                listener.addToFavorite(item)
                click_fav = true
            }
            else {
                holder.mBtnFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                listener.removeFavorite(item)
                click_fav = false
            }
        }
    }

    interface MobileListListener {
        fun gotoDetailPage(item: Mobiles)
        fun addToFavorite(item: Mobiles)
        fun removeFavorite(item: Mobiles)
    }
}


class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mImage = view.imageView
    val mTitle = view.titleTextView
    val mDescription = view.descriptionTextView
    val mPrice = view.pricetextView
    val mRating = view.ratingtextView
    val mBtnFavorite = view.btnFavorite

}