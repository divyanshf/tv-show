package com.example.movie_tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.movie_tv.data.model.User
import com.example.movie_tv.data.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel:UserViewModel
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var warningTextView: TextView

    fun onLogin(view: View){
        var user: User = userViewModel.getUser()
        var username:String = usernameEditText.text.toString()
        var password:String = passwordEditText.text.toString()
        if(username == user.username && password == user.password){
            user.isLogged = true
            userViewModel.update(user)
            finish()
        }
        else{
            Log.i("USERNAME", user.username)
            Log.i("PASSWORD", user.password)
            warningTextView.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = UserViewModel(application)
        usernameEditText = findViewById(R.id.username_edit_text) as TextInputEditText
        passwordEditText = findViewById(R.id.password_edit_text) as TextInputEditText
        warningTextView = findViewById(R.id.warning)
    }
}