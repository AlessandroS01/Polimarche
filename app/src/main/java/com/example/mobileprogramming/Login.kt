package com.example.mobileprogramming.com.example.mobileprogramming

import android.os.Bundle
import com.example.mobileprogramming.R
import com.google.android.material.textfield.TextInputEditText

class Login() {
        val matriculationNumber: String = findViewById<TextInputEditText>(R.id.MatricolaInput).text
        val password: String = findViewById<TextInputEditText>(R.id.PasswordInput).text

}
