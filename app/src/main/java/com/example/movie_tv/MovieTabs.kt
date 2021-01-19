package com.example.movie_tv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.movie_tv.auth.AuthActivity
import com.example.movie_tv.fragments.adapters.viewPagerAdapter
import com.example.movie_tv.fragments.FragmentWish
import com.example.movie_tv.fragments.FragmentWatching
import com.example.movie_tv.fragments.FragmentsWatched
import com.example.movie_tv.data.model.User
import com.example.movie_tv.data.viewmodel.UserViewModel
import com.example.movie_tv.fragments.FragmentsAdd
import com.google.android.material.tabs.TabLayout

class MovieTabs : AppCompatActivity(){
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tabs)

//      Initialize late init
        userViewModel = UserViewModel(application)

        //  Check if the user is logged
        var user: User? = userViewModel.getUser()
        if(!user?.isLogged!!){
            var intent: Intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        setUpTabs()
    }

    private fun setUpTabs()
    {
        val adapter = viewPagerAdapter(supportFragmentManager)
        adapter.addFragments(FragmentWish(),"")
        adapter.addFragments(FragmentWatching(),"")
        adapter.addFragments(FragmentsWatched(),"")
        adapter.addFragments(FragmentsAdd(),"")

        val viewPager= findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabLayout=findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_access_time_24)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_play_circle_outline_24)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_check_circle_outline_24)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_add_24)
    }

    override fun onResume() {
        super.onResume()

        setUpTabs()
    }
}