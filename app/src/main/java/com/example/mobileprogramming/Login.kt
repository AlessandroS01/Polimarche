package com.example.mobileprogramming.com.example.mobileprogramming

import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileprogramming.R

class Login : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val matriculationNumber = findViewById<EditText>(R.id.MatricolaInput).text
            val password = findViewById<EditText>(R.id.PasswordInput).text

            println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa")
            openResponsible()
    }

        private fun openResponsible(){
            setContentView(R.layout.main_responsible)
        }

}
