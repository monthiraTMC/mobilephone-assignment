package com.scb.mobilephone.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.presenter.ListInterface
import com.scb.mobilephone.presenter.ListPresenter

import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import com.scb.mobilephone.view.ListFragment.Companion.presenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private var positionTab = 0
    private var mListFragment: ListFragment = ListFragment()
    private var mFavoriteFragment: FavoriteFragment = FavoriteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        btnSort.setOnClickListener {
            showDialog()
        }
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

    private fun showDialog() {
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        val listItems = getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            //                mResult.setText(listItems[i])

            var sortType = listItems[i]

            showToast(listItems[i].toString())
            if(positionTab == 0) {
                presenter.getMobileList(sortType)
            }
            else {
                mFavoriteFragment.getType(sortType)
            }

            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()

    }
}


