package com.scb.mobilephone.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager

import androidx.appcompat.app.AppCompatActivity

import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.presenter.*

import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import com.scb.mobilephone.view.ListFragment.Companion.presenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainInterface.MainView {
    override fun getSortType(sortType: String) {
        Log.d("listsortM", sortType)
        mListFragment.getSortType(sortType)
    }


    private var positionTab = 0
    private var mListFragment: ListFragment = ListFragment()
    private var mFavoriteFragment: FavoriteFragment = FavoriteFragment()
    private lateinit var mMainPresenter: MainInterface.MainPresenter
//    private var sortType: String = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        mMainPresenter = MainPresenter(this, this@MainActivity, positionTab)
        btnSort.setOnClickListener { mMainPresenter.showDialog() }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    positionTab = 0
                } else {
                    positionTab = 1
                }
            }

        })

    }

}


