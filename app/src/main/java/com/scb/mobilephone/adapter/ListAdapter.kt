package com.scb.mobilephone.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephone.R
import com.scb.mobilephone.activity.DetailActivity
import com.scb.mobilephone.extensions.MOBILE_LIST
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

        holder.itemView.setOnClickListener {
            val item_positon = holder.adapterPosition
            if (item_positon != RecyclerView.NO_POSITION) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(MOBILE_LIST, mDataArray[position])
                context.startActivity(intent)
            }
        }
    }
}


class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
    var count_click: Int = 0
    val mImage = view.imageView
    val mTitle = view.titleTextView
    val mDescription = view.descriptionTextView
    val mPrice = view.pricetextView
    val mRating = view.ratingtextView
    val mFavorite = view.favolistImageView

    init {

        mFavorite.setOnClickListener {
            if (count_click == 0 ){
                mFavorite.setImageResource(R.drawable.ic_favorite_fillcolor)
                count_click = 1
            }
            else {
                mFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                count_click = 0
            }
        }


    }
}
