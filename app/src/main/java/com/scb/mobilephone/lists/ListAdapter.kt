package com.scb.mobilephone.lists

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.datails.DetailActivity
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.lists.ListFragment.Companion.presenter
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(val context: Context) : RecyclerView.Adapter<ListHolder>() {
    var mMobileArray: ArrayList<Mobiles> = ArrayList()
    var mFavoriteArray: ArrayList<Mobiles> = ArrayList()
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
        holder.mPrice.text = "Price: " + item.price.toString()
        holder.mRating.text = "Rating: " + item.rating.toString()
        Glide.with(context).load(item.thumbImageURL).into(holder.mImage)
        holder.itemView.setTag(R.id.view_pager, item.id)

        holder.itemView.setOnClickListener {
            val item_positon = holder.adapterPosition
            if (item_positon != RecyclerView.NO_POSITION) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(MOBILE_LIST, item)
                context.startActivity(intent)
            }
        }

        holder.mFavoriteToggle.text = null
        holder.mFavoriteToggle.textOn = null
        holder.mFavoriteToggle.textOff = null

        holder.mFavoriteToggle.isChecked = item in mFavoriteArray

        holder.mFavoriteToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (item in mFavoriteArray) {

                } else {
                    mFavoriteArray.add(item)
                    presenter.sendBroadcast(mFavoriteArray, context!!)
                }

            } else {
                mFavoriteArray.remove(item)
            }
            Log.d("favArrayListSend", mFavoriteArray.toString())
        }
    }
}


class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mImage = view.imageView
    val mTitle = view.titleTextView
    val mDescription = view.descriptionTextView
    val mPrice = view.pricetextView
    val mRating = view.ratingtextView
    val mFavoriteToggle = view.btnFavorite

}