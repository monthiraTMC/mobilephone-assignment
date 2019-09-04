package com.scb.mobilephone.favorites

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scb.mobilephone.database.AppDatabase
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.helper.CMWorkerThread
import com.scb.mobilephone.helper.CustomItemTouchHelperListener
import com.scb.mobilephone.helper.SortList
import com.scb.mobilephone.lists.ListInterface
import com.scb.mobilephone.model.Mobiles
import kotlin.NullPointerException as NullPointerException1

class FavoritePresenter(private val view: FavoriteInterface.FavoriteView, private val context: Context,
                        private val mThread: CMWorkerThread,
                        private val listener: SortListener) :
    FavoriteInterface.FavoritePresenter{
    override fun setupDatabase() {
        mDatabaseAdapter = AppDatabase.getInstance(context).also {
            it.openHelper.readableDatabase
        }
    }

    override fun getType(sortType: String) {
        mSortType = sortType
        listener.getSortList(mSortType, mFavoriteArray)
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllFavorite() {
        val task = Runnable {
            mFavoriteArray.clear()
            val result = mDatabaseAdapter?.favoriteDao()?.queryFavoriteLists()
            val gson = Gson()
            val json = gson.toJson(result)
            val data = gson.fromJson<List<Mobiles>>(json,object : TypeToken<List<Mobiles>>() {}.type)
            Log.d("getAllFavorite", data.toString())
            mFavoriteArray.addAll(data)
            view.getAllFavorite(mFavoriteArray)
            Log.d("databaseGetAll", mFavoriteArray.toString())
            Log.d("databaseGetAll", mFavoriteArray.size.toString())
        }
        mThread.postTask(task)
    }

    override fun removeFavorite(item: Mobiles) {
        Log.d("databaseRemove", item.toString())
        val task = Runnable {
            val result = mDatabaseAdapter!!.favoriteDao().queryFavoriteLists(item.id)
            if (result != null) {
                mDatabaseAdapter?.favoriteDao()?.deleteFromFavorite(result)
                context.showToast("Remove Favorite Successfully")
            }
        }
        mThread.postTask(task)
    }

    private var mSortType: String = "none"
    private var mDatabaseAdapter: AppDatabase? = null
    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()
    private var mMobileArray: ArrayList<Mobiles> = ArrayList()
    private var mFavoriteArray: ArrayList<Mobiles> = arrayListOf()
    private lateinit var mFavoritePresenter: FavoriteInterface.FavoriteView
    private var _mobiles: List<Mobiles> = listOf()

    interface SortListener {
        fun getSortList(sortType: String, mobiles: ArrayList<Mobiles>)
    }

}