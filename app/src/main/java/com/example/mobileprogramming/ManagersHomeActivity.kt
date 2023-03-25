package com.example.mobileprogramming

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManagersHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_home)

        val matriculation = intent.getIntExtra("EXTRA_MATRICULATION" , 0)
        val password = intent.getStringExtra("EXTRA_PASSWORD")

        val testo = findViewById<TextView>(R.id.textViewggggg)
        testo.text = "$matriculation and $password"
    }
}