package com.example.movie_tv.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.movie_tv.R

class FragmentAuth : Fragment(), View.OnClickListener {
    private var navController:NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.go_to_login).setOnClickListener(this)
        view.findViewById<Button>(R.id.go_to_register).setOnClickListener(this)
    }

    override fun onClick(view:View){
        when(view.id){
            R.id.go_to_login -> navController!!.navigate(R.id.action_fragmentAuth_to_fragmentLogin)
            R.id.go_to_register -> navController!!.navigate(R.id.action_fragmentAuth_to_fragmentRegister)
        }
    }
}