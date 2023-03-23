package com.example.mobileprogramming

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.mobileprogramming.com.example.mobileprogramming.Login
import com.google.android.material.textfield.TextInputLayout

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_interface)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT


        val signIn: Button = findViewById<Button>(R.id.SignInButton)


        signIn.setOnClickListener {
            val matriculationNumber = findViewById<EditText>(R.id.MatricolaInput).text
            val password = findViewById<EditText>(R.id.PasswordInput).text
            Login(matriculationNumber, password)
        }

    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }




}