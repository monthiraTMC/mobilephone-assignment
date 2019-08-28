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
import com.scb.mobilephone.fragment.ListFragment

import com.scb.mobilephone.model.Mobiles
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mListFragment:ListFragment
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

    }

    private fun showDialog() {
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        val listItems = getResources().getStringArray(R.array.sort_item)
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            //                mResult.setText(listItems[i])
            toast(listItems[i].toString())
            var sortType = listItems[i]

            ListFragment().feedData(sortType)
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


