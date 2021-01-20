package com.example.movie_tv

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.viewpager.widget.ViewPager
import com.example.movie_tv.auth.AuthActivity
import com.example.movie_tv.fragments.adapters.viewPagerAdapter
import com.example.movie_tv.fragments.FragmentWish
import com.example.movie_tv.fragments.FragmentWatching
import com.example.movie_tv.fragments.FragmentsWatched
import com.example.movie_tv.fragments.FragmentsAdd
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class MovieTabs : AppCompatActivity(){
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tabs)

//      Initialize late init
        mAuth = FirebaseAuth.getInstance()

//          Check if the user is logged
        val user = mAuth.currentUser
        if(user == null){
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        setUpTabs()
    }

    fun logout(view: View){
        MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
            .setTitle("Logout")
            .setMessage("Are you sure ?")
            .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                mAuth.signOut()
                this.recreate()
            })
            .setNegativeButton("NO", null)
            .show()
    }

    private fun setUpTabs()
    {
        val adapter = viewPagerAdapter(supportFragmentManager)
        adapter.addFragments(FragmentsAdd(),"")
        adapter.addFragments(FragmentWish(),"")
        adapter.addFragments(FragmentWatching(),"")
        adapter.addFragments(FragmentsWatched(),"")

        val viewPager= findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabLayout=findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_add_24)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_access_time_24)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_play_circle_outline_24)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_check_circle_outline_24)
    }

    override fun onResume() {
        super.onResume()

        setUpTabs()
    }
}