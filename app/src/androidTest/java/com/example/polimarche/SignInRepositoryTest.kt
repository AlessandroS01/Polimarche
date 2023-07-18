package com.example.polimarche

import androidx.test.ext.junit.rules.ActivityScenarioRule

import androidx.test.runner.AndroidJUnit4
import com.example.polimarche.users.all.menu.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Utilizza il framework di testing AndroidJUnit4
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule
    @JvmField
    // Test viene eseguito all'interno di MainActivity
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Istanza di FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun setup() {
        // Prima di ogni test, viene eseguito il metodo setup, che inizializza l'istanza di FirebaseAuth.
        firebaseAuth = FirebaseAuth.getInstance()
    }

    @After
    fun cleanup() {
        // Dopo ogni test, viene eseguito il metodo cleanup, che effettua il logout.
        firebaseAuth.signOut()
    }

    // Viene verificato se, con credenziali corrette, l'operazione di login è stata eseguita con successo.
    @Test
    fun signInWithCorrectCredentials() {
        val matricola = "1099336" // Matricola corretta per il test
        val matriculation = "s$matricola@studenti.univpm.it"
        val password = "1234567" // Password corretta per il test

        // Effettua il login con le credenziali corrette
        firebaseAuth.signInWithEmailAndPassword(matriculation, password)
            .addOnCompleteListener { task ->
                assert(task.isSuccessful) { "Login con credenziali corrette fallito" }
                println("Login avvenuto con successo")
            }
    }

    // Viene verificato se, con credenziali incorrette, l'operazione di login fallisce correttamente.
    @Test
    fun signInWithIncorrectCredentials() {
        val matricola = "1099336" // Matricola valida
        val matriculation = "s$matricola@studenti.univpm.it"
        val password = "wrongpassword" // Password errata

        // Effettua il login con credenziali errate
        firebaseAuth.signInWithEmailAndPassword(matriculation, password)
            .addOnCompleteListener { task ->
                assert(!task.isSuccessful) { "Login con credenziali errate avvenuto con successo" }
                println("Login con credenziali errate fallito")
            }
    }
}
