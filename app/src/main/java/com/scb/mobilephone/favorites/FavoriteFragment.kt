package com.scb.mobilephone.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.scb.mobilephone.R
import com.scb.mobilephone.database.DatabaseInterface
import com.scb.mobilephone.database.DatabasePresenter
import com.scb.mobilephone.extensions.THREAD_NAME
import com.scb.mobilephone.helper.*
import com.scb.mobilephone.lists.ListInterface
import com.scb.mobilephone.lists.ListPresenter
import com.scb.mobilephone.main.MainInterface
import com.scb.mobilephone.main.MainPresenter
import com.scb.mobilephone.model.Mobiles

class FavoriteFragment : Fragment(), FavoriteInterface.FavoriteView, SortInterface.SortToView,
    DatabaseInterface.DatabaseListener {

    override fun updateFavorite() {
        databasePresenter.getAllFavorite()
    }

    override fun getDBFavorite(list: ArrayList<Mobiles>) {
//        activity?.runOnUiThread {
            this.mFavoriteArray = list
            sortPresenter.sortMobileList(mSortType, mFavoriteArray)
//            mFavoriteAdapter.mFavoriteArray = mFavoriteArray
//            mFavoriteAdapter.notifyDataSetChanged()
//        }
        Log.d("mSortTypeFav01", mSortType)

    }

    override fun submitList(list: ArrayList<Mobiles>) {
        activity?.runOnUiThread {
            mFavoriteArray = list
            mFavoriteAdapter.mFavoriteArray = mFavoriteArray
            mFavoriteAdapter.notifyDataSetChanged()
        }
    }

    override fun getSortType(sortType: String) {
        mSortType = sortType
        sortPresenter.sortMobileList(mSortType, mFavoriteArray)
        Log.d("mSortTypeFav02", mSortType)
    }

    private var mSortType = "none"
    private lateinit var rvFavoriteList: RecyclerView
    private var mFavoriteArray: ArrayList<Mobiles> = ArrayList()
    private lateinit var mThread: CMWorkerThread
    private lateinit var sortPresenter: SortInterface.SortPresenter

    private lateinit var mFavoriteAdapter: FavoriteAdapter
    private lateinit var favoritePresenter: FavoriteInterface.FavoritePresenter
    private lateinit var databasePresenter: DatabaseInterface.DatabasePresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_favorites, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavoriteList = view.findViewById(R.id.recyclerViewFavorite)
        mThread = CMWorkerThread(THREAD_NAME).also { it.start() }
        databasePresenter = DatabasePresenter(this, context!!, mThread)
        mFavoriteAdapter = FavoriteAdapter(context!!, object : FavoriteAdapter.FavoriteListener {
            override fun removeFavorite(item: Mobiles) {
                databasePresenter.removeFavorite(item)
            }

        })
        rvFavoriteList.let {
            it.adapter = mFavoriteAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = DefaultItemAnimator()
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        sortPresenter = SortList(this)
        databasePresenter.setupDatabase()
        databasePresenter.getAllFavorite()

        val callback = CustomItemTouchHelperCallback(mFavoriteAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteList)
    }

}

