package com.scb.mobilephone.lists

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scb.mobilephone.database.AppDatabase
import com.scb.mobilephone.database.DatabaseEntity
import com.scb.mobilephone.datails.DetailActivity
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.THREAD_NAME
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.helper.CMWorkerThread
import com.scb.mobilephone.helper.SortList
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPresenter(_view: ListInterface.ListView, _context: Context) :
    ListInterface.ListPresenter {
    override fun getAllFavorite() {
        val task = Runnable {
            val result = mDatabaseAdapter?.favoriteDao()?.queryFavoriteLists()
            val gson = Gson()
            val json = gson.toJson(result)
            val data = gson.fromJson<List<Mobiles>>(json,object : TypeToken<List<Mobiles>>() {}.type)
            Log.d("getAllFavorite", data.toString())
            mFavoriteArray.addAll(data)
        }
        view.getAllFavorite(mFavoriteArray)
        mThread.postTask(task)
    }

    override fun addToFavorite(item: Mobiles) {
        Log.d("addToFavorite", item.toString())
        val task = Runnable {
            val result = mDatabaseAdapter!!.favoriteDao().queryFavoriteLists(item.id)
            if (result == null) {
                mDatabaseAdapter?.favoriteDao()?.addToFavorite(
                    DatabaseEntity(item.id, item.name, item.description, item.brand,
                        item.price, item.rating, item.thumbImageURL)
                )
                context.showToast("Add To Favorite Successfully")
            }

        }
        mThread.postTask(task)
    }

    override fun removeFavorite(item: Mobiles) {
        Log.d("removeFavorite", item.toString())
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

    override fun gotoDetailPage(item: Mobiles) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(MOBILE_LIST, item)
        context.startActivity(intent)
    }

    override fun addToMobileList(mobiles: ArrayList<Mobiles>) {
        _mobiles = mobiles
        mMobileArray.clear()
        mMobileArray.addAll(_mobiles)
        sort()
    }

    override fun getApiMobileList() {
        view.showLoading()
        mReceiveArray.clear()
        val call = ApiInterface.getClient().getMobileList()
        call.enqueue(object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                Log.d("getApi", t.message.toString())
                view.hideLoading()
                context.showToast("Cannot load api " + t.message.toString())
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("mobile-feed", response.toString())
                if (response.isSuccessful) {
                    context.showToast("Successfully")
                    mReceiveArray.addAll(response.body()!!)
                    Log.d("mobile-feed", mReceiveArray.toString())
                    view.showAllMobiles(mReceiveArray)
                    view.hideLoading()
                }

            }

        })

    }

    override fun getType(sortType: String) {
        mSortType = sortType
        sort()
    }

    fun sort() {
        mSortArray = mSortPresenter.sortMobileList(mMobileArray, mSortType)
        view.submitList(mSortArray)
    }
    private var mDatabaseAdapter:AppDatabase? = null
    private var view: ListInterface.ListView = _view
    private var context = _context
    private var mSortType: String = "none"
    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()
    private var mMobileArray: ArrayList<Mobiles> = ArrayList()
    private var mFavoriteArray: ArrayList<Mobiles> = arrayListOf()
    private var mSortArray: ArrayList<Mobiles> = ArrayList()
    private var mSortPresenter: SortList = SortList()
    private var _mobiles: List<Mobiles> = listOf()
    var mThread: CMWorkerThread = CMWorkerThread(THREAD_NAME).also {it.start()}


}