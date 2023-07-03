package com.example.polimarche.login

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.polimarche.users.all.menu.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun setup() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    @After
    fun cleanup() {
        firebaseAuth.signOut()
    }

    @Test
    fun signInWithCorrectCredentials() {
        val matricola = "1099336" // Inserisci la matricola corretta per il test
        val matriculation = "s$matricola@studenti.univpm.it"
        val password = "1234567" // Inserisci la password corretta per il test

        // Effettua il login con le credenziali corrette
        firebaseAuth.signInWithEmailAndPassword(matriculation, password)
            .addOnCompleteListener { task ->
                assert(task.isSuccessful) { "Login con credenziali corrette fallito" }
                println("Login avvenuto con successo")
            }
    }

    @Test
    fun signInWithIncorrectCredentials() {
        val matricola = "123456" // Inserisci una matricola valida
        val matriculation = "s$matricola@studenti.univpm.it"
        val password = "wrongpassword" // Inserisci una password errata

        // Effettua il login con credenziali errate
        firebaseAuth.signInWithEmailAndPassword(matriculation, password)
            .addOnCompleteListener { task ->
                assert(!task.isSuccessful) { "Login con credenziali errate avvenuto con successo" }
                println("Login con credenziali errate fallito")
            }
    }
}
