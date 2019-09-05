package com.scb.mobilephone.datails

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.datails.DetailInterface
import com.scb.mobilephone.datails.DetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailInterface.DetailView {

    private lateinit var viewPager:ViewPager
    private lateinit var viewPagerAapter: ViewPagerAdapter
    private lateinit var mPresenterDetail : DetailInterface.DetailPresenter
    private var width:Int = 0
    private var height:Int = 0
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mMobileList:Mobiles
    private lateinit var imageSlider: ImageSlider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewPager = findViewById(R.id.imageViewPager)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = (displayMetrics.heightPixels * 35) / 100

        mPresenterDetail = DetailPresenter(this, applicationContext!!)
        mMobileList = intent.extras?.getSerializable(MOBILE_LIST) as Mobiles
        mPresenterDetail.getDetail(mMobileList.id)

        detailTitle.setText(mMobileList.name)
        detailBrand.setText(mMobileList.brand)
        detailDescripstion.setText(mMobileList.description)
    }

    override fun showImageDetail(imageURL: ArrayList<String>) {
        viewPagerAapter = ViewPagerAdapter(this, imageURL, width, height)
        viewPager.adapter = viewPagerAapter
        viewPager.setAdapter(viewPagerAapter)


    }
}