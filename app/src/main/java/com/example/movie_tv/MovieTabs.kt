package com.example.movie_tv

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.movie_tv.auth.AuthActivity
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.example.movie_tv.fragments.FragmentWatching
import com.example.movie_tv.fragments.FragmentWish
import com.example.movie_tv.fragments.FragmentsAdd
import com.example.movie_tv.fragments.FragmentsWatched
import com.example.movie_tv.fragments.adapters.viewPagerAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieTabs : AppCompatActivity(){
    @Inject lateinit var mAuth : FirebaseAuth
    private val mMovieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tabs)

        //  Initialize late init
        mAuth = FirebaseAuth.getInstance()

        //  Check if the user is logged
        val user = mAuth.currentUser
        if(user == null){
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        setUpTabs()
    }

    //  Triggers on the logout button
    fun logout(view: View){
        val sharedPreferences = this.getSharedPreferences("com.example.movie_tv.auth", 0)!!
        MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
            .setTitle("Logout")
            .setMessage("Are you sure ?")
            .setPositiveButton("YES") { _, _ ->
                mAuth.signOut()
                mMovieViewModel.deleteAll()
                sharedPreferences.edit().putBoolean("SYNCED", false).apply()
                this.recreate()
            }
            .setNegativeButton("NO", null)
            .show()
    }

    //  Set the tabs up
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

    //  On activity resume after auth activity
    override fun onResume() {
        super.onResume()

        setUpTabs()
    }
}