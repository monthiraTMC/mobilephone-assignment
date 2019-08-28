package com.scb.mobilephone.adapter


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.activity.DetailActivity
import com.scb.mobilephone.extensions.*

import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.item_list.view.*


class ListAdapter(val context: Context) : RecyclerView.Adapter<ListHolder>() {
    private var mFillterArray: ArrayList<Mobiles> = ArrayList()
    private var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()
    val mobileList: List<Mobiles>
        get() = _mobiles

    private var _mobiles: List<Mobiles> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return ListHolder(view)
    }

    override fun getItemCount(): Int {
        return mobileList.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        var count_click: Int = 0
        var item = mFillterArray[position]
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
                intent.putExtra(MOBILE_LIST, mobileList[position])
                context.startActivity(intent)
            }
        }

        holder.mFavorite.setOnClickListener {
            if (count_click == 0 ){
                holder.mFavorite.setImageResource(R.drawable.ic_favorite_fillcolor)
                count_click = 1
                mFavoriteDataArray.add(mFillterArray[position])
                sendBroadcastMessage(mFavoriteDataArray)
            }
            else {
                holder.mFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                count_click = 0
                mFavoriteDataArray.remove(mFillterArray[position])
                sendBroadcastMessage(mFavoriteDataArray)
            }
        }

    }

    fun submitList(list: List<Mobiles>, sortType:String) {
        _mobiles = list
        mFillterArray.clear()
        when (sortType) {
            "Price low to high" -> {
                mFillterArray.addAll(_mobiles.sortedBy { it.price })
            }
            "Price high to low" -> {
                mFillterArray.addAll(_mobiles.sortedByDescending { it.price })
            }
            "Rating 5-1" -> {
                mFillterArray.addAll(_mobiles.sortedByDescending { it.rating })
            }
            else -> {
                mFillterArray.addAll(_mobiles)
            }
        }
        Log.d("sort", sortType)
        Log.d("sort", mFillterArray.toString())
        notifyDataSetChanged()

    }


    private fun sendBroadcastMessage(mFavoriteArray: ArrayList<Mobiles>) {
        Intent(RECEIVED_NEW_MESSAGE).let {
            it.putExtra(RECEIVED_FAVORITE, mFavoriteArray)
            LocalBroadcastManager.getInstance(context).sendBroadcast(it)
        }
    }
}


class ListHolder(view: View) : RecyclerView.ViewHolder(view) {

    val mImage = view.imageView
    val mTitle = view.titleTextView
    val mDescription = view.descriptionTextView
    val mPrice = view.pricetextView
    val mRating = view.ratingtextView
    val mFavorite = view.favolistImageView

}
