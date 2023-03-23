package com.example.mobileprogramming.com.example.mobileprogramming

import android.text.Editable

class Login(matriculationNumber: Editable, password: Editable) {

        val matriculationNumber = matriculationNumber
        val password = password

        init {
            println("${this.matriculationNumber}")
        }
}
