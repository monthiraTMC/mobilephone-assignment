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
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.RECEIVED_FAVORITE
import com.scb.mobilephone.extensions.RECEIVED_NEW_MESSAGE
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.view.DetailActivity
import kotlinx.android.synthetic.main.item_list.view.*


//class ListAdapter(val context: Context) : RecyclerView.Adapter<ListHolder>() {
//
//    private var mFillterArray: ArrayList<Mobiles> = ArrayList()
//    private var mFavoriteDataArray: ArrayList<Mobiles> = ArrayList()
//    private var mSortType:String = "none"
//    val mobileList: List<Mobiles>
//        get() = _mobiles
//
//    private var _mobiles: List<Mobiles> = listOf()
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
//        return ListHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return mFillterArray.size
//    }
//
//    override fun onBindViewHolder(holder: ListHolder, position: Int) {
//
//        var item = mFillterArray[position]
//        holder.mTitle.text = item.name
//        holder.mDescription.text = item.description
//        holder.mPrice.text = "Price: " + item.price.toString()
//        holder.mRating.text = "Rating: " + item.rating.toString()
//        Glide.with(context).load(item.thumbImageURL).into(holder.mImage)
//        holder.itemView.setTag(R.id.view_pager, item.id)
//
//        holder.itemView.setOnClickListener {
//            val item_positon = holder.adapterPosition
//            if (item_positon != RecyclerView.NO_POSITION) {
//                val intent = Intent(context, DetailActivity::class.java)
//                intent.putExtra(MOBILE_LIST, mobileList[position])
//                context.startActivity(intent)
//            }
//        }
//
//        holder.mFavoriteToggle.text = null
//        holder.mFavoriteToggle.textOn = null
//        holder.mFavoriteToggle.textOff = null
//
//        holder.mFavoriteToggle.isChecked = item in mFavoriteDataArray
//
//        holder.mFavoriteToggle.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                mFavoriteDataArray.add(mFillterArray[position])
//                sendBroadcastMessage(mFavoriteDataArray)
//            } else {
//                mFavoriteDataArray.remove(mFillterArray[position])
//                sendBroadcastMessage(mFavoriteDataArray)
//            }
//        }
//
//    }
//
//    fun submitList(list: List<Mobiles>) {
//        _mobiles = list
//        mFillterArray.clear()
//        mFillterArray.addAll(_mobiles)
//        sortList()
//        notifyDataSetChanged()
//
//    }
//
//    fun getType(sortType:String) {
//        mSortType = sortType
//        Log.d("sortList", mSortType)
//    }
//
//    fun sortList() {
//        mFillterArray.clear()
//        when (mSortType) {
//            "Price low to high" -> {
//                mFillterArray.addAll(_mobiles.sortedBy { it.price })
//            }
//            "Price high to low" -> {
//                mFillterArray.addAll(_mobiles.sortedByDescending { it.price })
//            }
//            "Rating 5-1" -> {
//                mFillterArray.addAll(_mobiles.sortedByDescending { it.rating })
//            }
//            else -> {
//                mFillterArray.addAll(_mobiles)
//            }
//        }
//        Log.d("sortListM", mSortType)
//        Log.d("sortListM", mFillterArray.toString())
//
//        notifyDataSetChanged()
//    }
//
//    fun reciveFavoriteList(list: ArrayList<Mobiles>) {
//        mFavoriteDataArray.clear()
//        mFavoriteDataArray.addAll(list)
//        notifyDataSetChanged()
//        Log.d("reciveFav", mFavoriteDataArray.toString())
//
//    }
//
//    private fun sendBroadcastMessage(mFavoriteArray: ArrayList<Mobiles>) {
//        Intent(RECEIVED_NEW_MESSAGE).let {
//            it.putExtra(RECEIVED_FAVORITE, mFavoriteArray)
//            LocalBroadcastManager.getInstance(context).sendBroadcast(it)
//        }
//    }
//}
//
//
//class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//    val mImage = view.imageView
//    val mTitle = view.titleTextView
//    val mDescription = view.descriptionTextView
//    val mPrice = view.pricetextView
//    val mRating = view.ratingtextView
//    val mFavoriteToggle = view.btnFavorite
//
//}
