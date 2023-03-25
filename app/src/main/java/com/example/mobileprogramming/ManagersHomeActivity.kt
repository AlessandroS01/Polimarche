package com.example.mobileprogramming

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManagersHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_home)


        val matriculation = intent.getStringExtra("EXTRA_MATRICULATION").toString()
        val password = intent.getStringExtra("EXTRA_PASSWORD").toString()

        val testo = findViewById<TextView>(R.id.textViewggggg)
        testo.text = "$matriculation and $password"
    }
}