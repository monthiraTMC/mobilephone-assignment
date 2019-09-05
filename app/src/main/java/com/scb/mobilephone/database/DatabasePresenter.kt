package com.scb.mobilephone.database

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.helper.CMWorkerThread
import com.scb.mobilephone.model.Mobiles

class DatabasePresenter(private val listener: DatabaseInterface.DatabaseListener,
                        private val context: Context,
                        private val mThread: CMWorkerThread): DatabaseInterface.DatabasePresenter {
    override fun getAllFavorite() {
        val task = Runnable {
            mFavoriteArray.clear()
            val result = mDatabaseAdapter?.favoriteDao()?.queryFavoriteLists()
            val gson = Gson()
            val json = gson.toJson(result)
            val data = gson.fromJson<List<Mobiles>>(json,object : TypeToken<List<Mobiles>>() {}.type)
            Log.d("getAllFavorite", data.toString())
            mFavoriteArray.addAll(data)
            listener.getDBFavorite(mFavoriteArray)
        }
        mThread.postTask(task)
    }

    override fun addToFavorite(item: Mobiles) {
        val task = Runnable {
            val result = mDatabaseAdapter!!.favoriteDao().queryFavoriteLists(item.id)
            if (result == null) {
                mDatabaseAdapter?.favoriteDao()?.addToFavorite(
                    DatabaseEntity(item.id, item.name, item.description, item.brand,
                        item.price, item.rating, item.thumbImageURL)
                )
                context.showToast("Add To Favorite Successfully")
                Log.d("databaseAdd", item.toString())
            }
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

    override fun setupDatabase() {
        mDatabaseAdapter = AppDatabase.getInstance(context).also {
            it.openHelper.readableDatabase
        }
    }
    private var mDatabaseAdapter:AppDatabase? = null
    private var mFavoriteArray: ArrayList<Mobiles> = arrayListOf()
}