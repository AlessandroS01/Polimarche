package com.example.mobileprogramming

import android.text.Editable
import java.security.MessageDigest

class Login(matriculationNumber: String, password: String) {

    init {
        val encryptedPassword = sha256(password)

    }

    /*
        This method return the password used inside
        the login interface already encrypted in sha-256
     */
    private fun sha256(password: String): String {
        val bytes = password.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}