package com.example.movie_tv.auth

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.movie_tv.R
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FragmentRegister : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var cnfPasswordEditText: TextInputEditText
    private lateinit var warningTextView: TextView
    private lateinit var mAuth:FirebaseAuth
    private lateinit var fire:FirebaseFirestore
    private lateinit var sharedPreferences:SharedPreferences

    private fun registerUser(){
        var username = usernameEditText.text.toString()
        var password = passwordEditText.text.toString()

        mAuth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(OnCompleteListener<AuthResult>(){
                if(it.isSuccessful){

                    fire.collection("users")
                        .document(username)
                        .set({})
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener { exception ->
                            Log.i("REGISTER", exception.toString())
                        }

                    //  Finish the activity
                    activity?.finish()
                }else{
                    warningTextView.text = "Please enter valid credentials!"
                    warningTextView.visibility = View.VISIBLE
                }
            })
    }

    private fun validate():Boolean{
        if(passwordEditText.text.toString() == cnfPasswordEditText.text.toString())
            return true
        warningTextView.text = "Password doesn't match!"
        warningTextView.visibility = View.VISIBLE
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        usernameEditText = view.findViewById(R.id.username_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        cnfPasswordEditText = view.findViewById(R.id.cnf_password_edit_text)
        warningTextView = view.findViewById(R.id.warning)
        mAuth = FirebaseAuth.getInstance()
        fire = FirebaseFirestore.getInstance()
        sharedPreferences =  context?.getSharedPreferences("com.example.movie_tv.auth", 0)!!

        view.findViewById<Button>(R.id.button_register).setOnClickListener {
            if(validate()){
                registerUser()
            }
        }

        return view
    }
}