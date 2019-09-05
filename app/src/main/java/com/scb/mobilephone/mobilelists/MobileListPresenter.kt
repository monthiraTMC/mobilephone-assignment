package com.scb.mobilephone.mobilelists

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.scb.mobilephone.mobiledatails.DetailActivity
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.model.Mobiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileListPresenter(private val view: MobileListInterface.MobileListView, private val context: Context) :
    MobileListInterface.MobileListPresenter {

    override fun gotoDetailPage(item: Mobiles) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(MOBILE_LIST, item)
        context.startActivity(intent)
    }

    override fun getApiMobileList() {
        view.showLoading()
        mReceiveArray.clear()
        val call = ApiInterface.getClient().getMobileList()
        call.enqueue(object : Callback<List<Mobiles>> {
            override fun onFailure(call: Call<List<Mobiles>>, t: Throwable) {
                Log.d("getApi", t.message.toString())
                view.hideLoading()
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Error")
                builder.setMessage(" Cannot call API ")
                builder.setNeutralButton("OK"){_,_ ->
                    context.showToast("Cannot call API " + t.message.toString())
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            override fun onResponse(call: Call<List<Mobiles>>, response: Response<List<Mobiles>>) {
                Log.d("mobile-feed", response.toString())
                if (response.isSuccessful) {
                    view.hideLoading()
                    context.showToast("Successfully")
                    mReceiveArray.addAll(response.body()!!)
                    Log.d("mobile-feed", mReceiveArray.toString())
                    view.showAllMobiles(mReceiveArray)
                }

            }

        })

    }

    private var mReceiveArray: ArrayList<Mobiles> = ArrayList()

}