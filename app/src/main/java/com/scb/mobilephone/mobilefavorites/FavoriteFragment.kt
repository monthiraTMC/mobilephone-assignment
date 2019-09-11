package com.scb.mobilephone.mobilefavorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.scb.mobilephone.R
import com.scb.mobilephone.database.DatabaseInterface
import com.scb.mobilephone.database.DatabasePresenter
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.THREAD_NAME
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.helper.CMWorkerThread
import com.scb.mobilephone.helper.CustomItemTouchHelperCallback
import com.scb.mobilephone.helper.SortInterface
import com.scb.mobilephone.mobiledetails.DetailActivity
import com.scb.mobilephone.model.Mobiles

class FavoriteFragment : Fragment(), SortInterface.SortToView,
    DatabaseInterface.DatabaseListener {
    override fun showToastMessage(message: String) {
        context?.showToast(message)
    }

    override fun updateFavorite() {
        databasePresenter.getAllFavorite()
    }

    override fun getDBFavorite(list: ArrayList<Mobiles>) {
        favoritePresenter.getFavorite(list)
    }

    override fun submitList(list: ArrayList<Mobiles>) {
        activity?.runOnUiThread {
            mFavoriteAdapter.mFavoriteArray = list
            mFavoriteAdapter.notifyDataSetChanged()
        }
    }

    override fun getSortType(sortType: String) {
       favoritePresenter.getType(sortType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavoriteList = view.findViewById(R.id.recyclerViewFavorite)
        mThread = CMWorkerThread(THREAD_NAME).also { it.start() }
        databasePresenter = DatabasePresenter(this, context!!, mThread)
        mFavoriteAdapter = FavoriteAdapter(context!!, object : FavoriteAdapter.FavoriteListener {
            override fun gotoDetailPage(item: Mobiles) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(MOBILE_LIST, item)
                startActivity(intent)
            }

            override fun removeFavorite(item: Mobiles) {
                databasePresenter.removeFavorite(item)
                databasePresenter.getAllFavorite()
            }

        })
        rvFavoriteList.let {
            it.adapter = mFavoriteAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator()
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        favoritePresenter = FavoritePresenter(this)
        databasePresenter.setupDatabase()

        val callback = CustomItemTouchHelperCallback(mFavoriteAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteList)
    }

    private lateinit var favoritePresenter: FavoriteInterface.FavoritePresenter
    private lateinit var rvFavoriteList: RecyclerView
    private lateinit var mThread: CMWorkerThread
    private lateinit var mFavoriteAdapter: FavoriteAdapter
    private lateinit var databasePresenter: DatabaseInterface.DatabasePresenter
}

