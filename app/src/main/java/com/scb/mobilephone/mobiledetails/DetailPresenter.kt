package com.scb.mobilephone.mobiledetails

import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.MobileDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(private val view: DetailInterface.DetailView):
    DetailInterface.DetailPresenter {
    private val mDetailArray : ArrayList<MobileDetail> = ArrayList()
    private var mImageArray: ArrayList<String> = arrayListOf()

    override fun getDetail(mobile_id:Int) {
        val call = mobile_id.let { ApiInterface.getClient().getImage(it.toString()) }
        //check request
        call.enqueue(object : Callback<List<MobileDetail>> {
            override fun onFailure(call: Call<List<MobileDetail>>, t: Throwable) {
                view.showDialog()
            }

            override fun onResponse(call: Call<List<MobileDetail>>, response: Response<List<MobileDetail>>) {
                if (response.isSuccessful) {
                    mDetailArray.clear()
                    mDetailArray.addAll(response.body()!!)
                    for (i in 0 until mDetailArray.size) {
                        var url = mDetailArray[i].url
                        if (url.contains("http")) {}
                        else url = "https://$url"
                        mImageArray.add(i, url)
                    }
                    view.showImageDetail(mImageArray)
                    view.showToastMessage("Load image Successfully")
                }
            }
        })
    }
}