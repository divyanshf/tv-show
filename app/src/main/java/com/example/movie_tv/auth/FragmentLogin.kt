package com.example.movie_tv.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.movie_tv.R
import com.example.movie_tv.data.model.User
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.example.movie_tv.data.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class FragmentLogin : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var warningTextView: TextView

    private fun loginUser(){
        var username = usernameEditText.text as String
        var password = passwordEditText.text as String
        var user:User? = userViewModel.getUser()

        if(user != null){
            user.isLogged = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        usernameEditText = view.findViewById(R.id.username_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        warningTextView = view.findViewById(R.id.warning)

        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            loginUser()
        }

        return view
    }

}