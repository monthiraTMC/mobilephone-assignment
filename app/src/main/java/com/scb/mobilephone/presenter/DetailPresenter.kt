package com.scb.mobilephone.presenter;

import android.content.Context
import android.util.Log
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.MobileDetail
import com.scb.mobilephone.model.Mobiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(_view : DetailInterface.DetailView, context: Context): DetailInterface.DetailPresenter {
    private val view = _view
    private val mDetailArray : ArrayList<MobileDetail> = ArrayList()
    private var mImageArray: ArrayList<String> = arrayListOf()
    private var context = context

    override fun getDetail(mobile_id:Int) {
        view.showLoading()
        val call = mobile_id?.let { ApiInterface.getClient().getImage(it.toString()) }
        //check request
        Log.d("MOBILE_IMAGE", call.request().url().toString())
        call.enqueue(object : Callback<List<MobileDetail>> {
            override fun onFailure(call: Call<List<MobileDetail>>, t: Throwable) {
                Log.d("MOBILE_IMAGE", t.message.toString())
                context.showToast("Cannot load image " + t.message.toString())
            }

            override fun onResponse(call: Call<List<MobileDetail>>, response: Response<List<MobileDetail>>) {
                Log.d("MOBILE_IMAGE", response.body().toString())
                if (response.isSuccessful) {
                    view.hideLoading()
                    mDetailArray.clear()
                    mDetailArray.addAll(response.body()!!)

                    for (i in 0..mDetailArray.size - 1) {
                        var url = mDetailArray[i].url
                        if (url.contains("http")) {
                            url = url
                        }
                        else {
                            url = "https://" + url
                        }
                        mImageArray.add(i, url)

                    }
                    view.showImageDetail(mImageArray)
                    context.showToast("Successfully")

                }

            }

        })
    }

}