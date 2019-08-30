package com.scb.mobilephone.view

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.model.MobileDetail
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.model.ApiInterface
import com.scb.mobilephone.presenter.DetailInterface
import com.scb.mobilephone.presenter.DetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity(), DetailInterface.DetailView {

    private lateinit var mPresenterDetail : DetailInterface.DetailPresenter
    private var width:Int = 0
    private var height:Int = 0
    private lateinit var mMobileList:Mobiles
    private lateinit var imageSlider: ImageSlider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        mPresenterDetail = DetailPresenter(this)
        mMobileList = intent.extras?.getSerializable(MOBILE_LIST) as Mobiles
        mPresenterDetail.getDetail(mMobileList.id)

        detailTitle.setText(mMobileList.name)
        detailBrand.setText(mMobileList.brand)
        detailDescripstion.setText(mMobileList.description)
    }

    override fun showImageDetail(imageURL: ArrayList<String>) {
        imageSlider = findViewById(R.id.slider)
        imageSlider.adapter = SliderAdapter(
            applicationContext,
            PicassoImageLoaderFactory(),
            imageUrls = imageURL
        )
    }


}