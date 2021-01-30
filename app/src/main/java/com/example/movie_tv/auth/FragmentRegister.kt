package com.example.movie_tv.auth

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.movie_tv.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentRegister : Fragment() {
    private lateinit var usernameEditText: TextInputEditText
    @Inject lateinit var mAuth:FirebaseAuth
    @Inject lateinit var fire:FirebaseFirestore
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var cnfPasswordEditText: TextInputEditText
    private lateinit var warningTextView: TextView
    private lateinit var sharedPreferences:SharedPreferences

    //  Remove any extra spacing from the email address
    private fun formatUsername(username:String) : String{
        return username.filter {
            !it.isWhitespace()
        }
    }

    //  Register the user
    @SuppressLint("SetTextI18n")
    private fun registerUser(){
        var username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        username = formatUsername(username)
        usernameEditText.setText(username)

        if(username.isNotEmpty() and password.isNotEmpty()){
            mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

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
                    } else {
                        warningTextView.text = "Please enter valid credentials!"
                        warningTextView.visibility = View.VISIBLE
                    }
                }
        }
        else{
            Toast.makeText(context, "Invalid value", Toast.LENGTH_SHORT).show()
        }
    }

    //  Confirm the password
    @SuppressLint("SetTextI18n")
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