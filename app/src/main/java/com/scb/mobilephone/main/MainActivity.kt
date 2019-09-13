package com.scb.mobilephone.main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private var checkedItem = -1
    lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sectionsPagerAdapter = SectionsPagerAdapter( supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        btnSort.setOnClickListener {
            showDialog()
        }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                sectionsPagerAdapter.updateFavorite()
            }
        })

    }

    private fun showDialog() {
        val mBuilder = AlertDialog.Builder(this)
        val listItems = this.getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, checkedItem) { dialogInterface, i ->
            val sortType = listItems[i]
            checkedItem = i
            sectionsPagerAdapter.getSortType(sortType)
            this.showToast(listItems[i].toString())
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }
}


