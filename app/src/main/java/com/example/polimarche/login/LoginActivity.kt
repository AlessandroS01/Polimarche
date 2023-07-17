package com.example.polimarche.login

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.R
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // savedInstanceState è oggetto Bundle (contenitore di dati) che contiene i dati salvati dal precedente stato dell'activity
        // super si riferisce alla superclasse AppCompatActivity
        FirebaseApp.initializeApp(this) // inizializza Firebase nel contesto corrente dell'activity
        setContentView(R.layout.activity_login) // metodo imposta il layout dell'activity specificando il file XML activity_login
                                                            // come il layout da visualizzare

        // imposta un flag per indicare che la barra di stato non deve essere traslucida
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)

        // imposta il colore della barra di stato su trasparente
        window.statusBarColor = Color.TRANSPARENT

        // Imposta la visibilità dell'interfaccia utente per nascondere la barra di navigazione nell'activity
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val loginFragment = LoginFragment() // viene creato un oggetto di tipo LoginFragment

        // Inizia una transazione del FragmentManager per effettuare una modifica al layout corrente.
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutLoginSignIn, loginFragment).commit() // sostituisce il contenuto del frameLayoutLoginSignIn con l'istanza di loginFragment
            setReorderingAllowed(true) // riordinamento delle transazioni
            addToBackStack(null) // aggiungere la transazione corrente nello stack di transazioni all'indietro
        }
    }

    /*
        Questo metodo viene utilizzato per impostare la barra di stato
        completamente trasparente
     */
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

    // Questa funzione sposta l'attività in background anziché terminarla immediatamente
    override fun onBackPressed(){
        moveTaskToBack(false) // False indica che l'attività non deve essere terminata
    }


}
