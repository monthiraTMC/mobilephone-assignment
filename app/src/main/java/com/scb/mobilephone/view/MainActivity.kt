package com.scb.mobilephone.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.scb.mobilephone.R
import com.scb.mobilephone.presenter.MainInterface
import com.scb.mobilephone.presenter.MainPresenter
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import com.scb.mobilephone.extensions.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainInterface.MainView {

    private var mListFragment: ListFragment = ListFragment()
    private var mFavoriteFragment: FavoriteFragment = FavoriteFragment()
    private lateinit var mMainPresenter: MainInterface.MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        mMainPresenter = MainPresenter(this, this@MainActivity)
        btnSort.setOnClickListener { mMainPresenter.showDialog() }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                mMainPresenter.getPosition(position)
            }

        })


    }

}


