package com.example.polimarche.users.managers.menu.tracks

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.polimarche.R
import com.example.polimarche.data_container.track.TracksViewModel
import com.example.polimarche.users.all.menu.main.MainActivity
import com.example.polimarche.users.all.menu.tracks.SeeTracksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class ManagersTracksActivity : AppCompatActivity(){

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)// savedInstanceState è oggetto Bundle (contenitore di dati) che
        // contiene i dati salvati dal precedente stato dell'activity
        // super si riferisce alla superclasse AppCompatActivity

        setContentView(R.layout.activity_managers_tracks)// metodo imposta il layout
        // dell'activity specificando il file XML ...
        // come il layout da visualizzare

        // imposta un flag per indicare che la barra di stato non deve essere traslucida
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)

        // imposta il colore della barra di stato su trasparente
        window.statusBarColor = Color.TRANSPARENT

        // Imposta la visibilità dell'interfaccia utente per nascondere la barra di navigazione nell'activity
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        /*
        Questa parte del codice elimina l'ombra di sfondo
        creata dalla bottomNavigationView
         */

        // riferimento all'oggetto BottomNavigationView dall'XML del layout utilizzando il suo ID
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.tracksBottomNavigationView)

        // imposta il padding su tutti i lati a 0 pixel
        bottomNavigationView.setPadding(0, 0 , 0, 0)
        //si setta a null l'ascoltatore per le modifiche
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        // indica che l'etichetta sarà visibile solo quando il relativo menu sarà selezionato
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView.background = null // imposta lo sfondo su null

        /*
        Questa parte permette all'utente di tornare indietro alla pagina principale
        cliccando sul bottone per tornare indietro nella parte alta dello schermo
         */
        val backButton = findViewById<ImageButton>(R.id.backButtonTracks)
        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }


        /*
        Parte che ci permette di cambiare i Fragments dentro
        R.id.frameSetupManager al tocco dei bottoni
        nella bottomNavigationBar
        */
        val seeTracksFragment = SeeTracksFragment()
        val deleteTracksFragment = DeleteTrackFragment()
        setCurrentFragment(seeTracksFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.see_tracks -> setCurrentFragment(seeTracksFragment)
                R.id.delete_tracks -> setCurrentFragment(deleteTracksFragment)
            }
            true
        }



    }


    // Metodo utlizzato per settare la status bar trasparente
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

    private fun setCurrentFragment(fragment : Fragment){
        // Inizia una transazione del FragmentManager per effettuare una modifica
        // al layout corrente
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameTracksManagers, fragment).commit()// sostituisce il contenuto
            // del frameTracksManagers con l'istanza di fragment passata come parametro
            setReorderingAllowed(true) // riordinamento delle transazioni
            addToBackStack(null)// aggiungere la transazione corrente nello
                                      // stack di transazioni all'indietro
        }
    }
}