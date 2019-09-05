package com.scb.mobilephone.mobiledetails

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailInterface.DetailView {

    private lateinit var viewPager:ViewPager
    private lateinit var viewPagerAapter: ViewPagerAdapter
    private lateinit var mPresenterDetail : DetailInterface.DetailPresenter
    private var width:Int = 0
    private var height:Int = 0
    private lateinit var mMobileList:Mobiles

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
        val params = LinearLayout.LayoutParams(width, height)
        viewPager.setLayoutParams(params)
        viewPager.adapter = viewPagerAapter
        viewPager.setAdapter(viewPagerAapter)
    }
}