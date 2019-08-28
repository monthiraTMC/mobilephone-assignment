package com.scb.mobilephone.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.R
import com.scb.mobilephone.extensions.showToast
import com.scb.mobilephone.fragment.FavoriteFragment
import com.scb.mobilephone.fragment.ListFragment

import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.*

class MainActivity : AppCompatActivity() {
    private var positionTab = 0
    private var mListFragment : ListFragment = ListFragment()
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
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    positionTab = 0
                }else{
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
            if (positionTab == 0){
                showToast(listItems[i].toString())
                mListFragment.loadMobileList(sortType)
            }

            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()

    }
}


