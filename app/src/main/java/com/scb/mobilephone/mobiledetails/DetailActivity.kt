package com.scb.mobilephone.mobiledetails

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.MOBILE_LIST
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.model.Mobiles
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailInterface.DetailView {

    override fun showToastMessage(message: String) {
        this.showToast(message)
    }

    override fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(" Cannot call API, Try again?")
        builder.setPositiveButton("YES") { _, _ ->
            mPresenterDetail.getDetail(mMobileList.id)
        }

        builder.setNegativeButton("NO") { _, _ ->
            finishAffinity()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = (displayMetrics.heightPixels * 35) / 100

        mPresenterDetail = DetailPresenter(this)
        mMobileList = intent.extras?.getSerializable(MOBILE_LIST) as Mobiles
        mPresenterDetail.getDetail(mMobileList.id)

        detailTitle.setText(mMobileList.name)
        detailBrand.setText(mMobileList.brand)
        detailDescripstion.setText(mMobileList.description)
    }

    override fun showImageDetail(imageURL: ArrayList<String>) {
        val viewPagerAapter = ViewPagerAdapter(this, imageURL, width, height)
        val params = LinearLayout.LayoutParams(width, height)
        imageViewPager.setLayoutParams(params)
        imageViewPager.adapter = viewPagerAapter
        imageViewPager.setAdapter(viewPagerAapter)
    }

    private lateinit var mPresenterDetail: DetailInterface.DetailPresenter
    private var width: Int = 0
    private var height: Int = 0
    private lateinit var mMobileList: Mobiles

}