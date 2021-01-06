package com.example.movie_tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.movie_tv.Fragments.Adapters.viewPagerAdapter
import com.example.movie_tv.Fragments.Fragment_Wish
import com.example.movie_tv.Fragments.Fragment_watching
import com.example.movie_tv.Fragments.Fragments_watched
import com.google.android.material.tabs.TabLayout

class movieTabs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tabs)


        setUpTabs()
    }

    private fun setUpTabs()
    {
        val adapter = viewPagerAdapter(supportFragmentManager)
        adapter.addFragments(Fragment_Wish(),"")
        adapter.addFragments(Fragment_watching(),"")
        adapter.addFragments(Fragments_watched(),"")

        val viewPager= findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabLayout=findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)




        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_access_time_24)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_play_circle_outline_24)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_check_circle_outline_24)





    }
}