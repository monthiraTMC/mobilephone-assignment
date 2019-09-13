package com.scb.mobilephone.mobilelists

import com.scb.mobilephone.helper.SortInterface
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MobileListPresenter(private val view: MobileListInterface.MobileListView,
                          private val sortPresenter: SortInterface.SortPresenter) :
    MobileListInterface.MobileListPresenter {

    override fun showAllMobile(list: ArrayList<Mobiles>) {
        this.mMobileArray = list
        sortPresenter.sortMobileList(mSortType, mMobileArray)
    }

    override fun getFavorite(list: ArrayList<Mobiles>) {
        view.notifyFavoriteChanged(list)
    }

    override fun getSortType(sortType: String) {
        this.mSortType = sortType
        sortPresenter.sortMobileList(mSortType, mMobileArray)
    }

    override fun getApiMobileList() {
        view.showLoading()
        val mReceiveArray = arrayListOf<Mobiles>()
        val call = ApiInterface.getClient().getMobileList()

        call.enqueue(
            object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                view.hideLoading()
                view.showDialog()
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                if (response.isSuccessful) {
                    view.hideLoading()
                    view.showToastMessage("Successfully")
                    mReceiveArray.addAll(response.body()!!)
                    view.getAllMobiles(mReceiveArray)
                }
            }
        })
    }

    private var mSortType = "none"
    private var mMobileArray: ArrayList<Mobiles> = ArrayList()
}