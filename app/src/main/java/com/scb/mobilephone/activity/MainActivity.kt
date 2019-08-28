package com.scb.mobilephone.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.R
import com.scb.mobilephone.fragment.ListFragment.Companion.mDataArray
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var sortType:String? = null
    private var ascending = true
    private val array: ArrayList<Int> = arrayListOf(3,1,2,5,4)
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

                sortData(ascending)
                ascending != ascending

        }
    }

    private fun sortData(asc: Boolean) {
        if (asc) {
            mDataArray.price
            array.sort()
            Log.d("sort", array.toString())
        }
    }

    private fun showDialog() {
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        val listItems = getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            //                mResult.setText(listItems[i])
            toast(listItems[i].toString())
            sortType = listItems[i]
            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()

    }
}


// Extension function to show toast message
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


