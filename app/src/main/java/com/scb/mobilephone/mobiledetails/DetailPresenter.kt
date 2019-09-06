package com.scb.mobilephone.mobiledetails

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.MobileDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(private val view: DetailInterface.DetailView, private var context: Context):
    DetailInterface.DetailPresenter {
    private val mDetailArray : ArrayList<MobileDetail> = ArrayList()
    private var mImageArray: ArrayList<String> = arrayListOf()

    override fun getDetail(mobile_id:Int) {
        val call = mobile_id.let { ApiInterface.getClient().getImage(it.toString()) }
        //check request
        Log.d("MOBILE_IMAGE", call.request().url().toString())
        call.enqueue(object : Callback<List<MobileDetail>> {
            override fun onFailure(call: Call<List<MobileDetail>>, t: Throwable) {
                Log.d("MOBILE_IMAGE", t.message.toString())
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Error")
                builder.setMessage(" Cannot call API, Try again?")
                builder.setPositiveButton("YES"){_, _->
                    getDetail(mobile_id)
                }

                builder.setNegativeButton("NO"){_, _->
                    view.closeApp()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            override fun onResponse(call: Call<List<MobileDetail>>, response: Response<List<MobileDetail>>) {
                Log.d("MOBILE_IMAGE", response.body().toString())
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
                    context.showToast("Load image Successfully")
                }
            }
        })
    }
}