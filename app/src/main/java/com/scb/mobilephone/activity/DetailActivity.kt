package com.scb.mobilephone.activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.custom.sliderimage.logic.SliderImage
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.model.MobileDetail
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.network.ApiInterface
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private val mDetailArray : ArrayList<MobileDetail> = ArrayList()
    companion object{
        var mImageArray: ArrayList<String> = arrayListOf()
    }
    private var width:Int = 0
    private var height:Int = 0


    private var url:String? = null
    private lateinit var mMobileList:Mobiles
    private lateinit var imageSlider: ImageSlider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        mMobileList = intent.extras?.getSerializable(MOBILE_LIST) as Mobiles
        setDetailText()
        feedImage(mMobileList.id)




    }






    private fun setDetailText() {
        detailTitle.setText(mMobileList.name)
        detailBrand.setText(mMobileList.brand)
        detailDescripstion.setText(mMobileList.description)

    }

    private fun feedImage(mobile_id:Int) {
        //template connect to network
        val call = mobile_id?.let { ApiInterface.getClient().getImage(it.toString()) }

        //check request
        Log.d("MOBILE_IMAGE", call.request().url().toString())
        call.enqueue(object : Callback<List<MobileDetail>> {
            override fun onFailure(call: Call<List<MobileDetail>>, t: Throwable) {
                Log.d("MOBILE_IMAGE", t.message.toString())
            }

            override fun onResponse(call: Call<List<MobileDetail>>, response: Response<List<MobileDetail>>) {
                Log.d("MOBILE_IMAGE", response.body().toString())
                if (response.isSuccessful) {
                    mDetailArray.clear()
                    mDetailArray.addAll(response.body()!!)

                    for (i in 0..mDetailArray.size - 1) {
                        url = mDetailArray[i].url
                        if (url!!.contains("http")) {
                            url = url
                        }
                        else {
                            url = "https://" + url
                        }
                        mImageArray.add(i, url!!)

                    }
                    Log.d("MOBILE_IMAGE", mImageArray.toString())

                    imageSlider = findViewById(R.id.slider)
                    imageSlider.adapter = SliderAdapter(
                        applicationContext,
                        PicassoImageLoaderFactory(),
                        imageUrls = mImageArray
                    )

                }

            }

        })

    }

}