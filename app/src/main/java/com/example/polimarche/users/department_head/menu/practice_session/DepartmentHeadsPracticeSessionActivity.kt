package com.example.polimarche.users.department_head.menu.practice_session

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.databinding.ActivityDepartmentHeadsPracticeSessionBinding
import com.example.polimarche.users.all.menu.main.MainActivity
import com.example.polimarche.users.all.menu.practice_session.SeePracticeSessionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class DepartmentHeadsPracticeSessionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDepartmentHeadsPracticeSessionBinding

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)// savedInstanceState è oggetto Bundle (contenitore di dati) che
        // contiene i dati salvati dal precedente stato dell'activity
        // super si riferisce alla superclasse AppCompatActivity
        binding = ActivityDepartmentHeadsPracticeSessionBinding.inflate(layoutInflater)
        //crea un'istanza di ActivityDepartmentHeadsPracticeSessionBinding
        // che è in grado di accedere agli elementi della vista definiti nel layout XML associato
        // a ActivityDepartmentHeadsPracticeSessionBinding.
        setContentView(binding.root) //binding.root rappresenta l'elemento radice del layout inflato
                                    // che viene settato come layout dell'activity.

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
        val bottomNavigationView = findViewById<BottomNavigationView>(binding.dHPracticeSessionBottomNavigationView.id)
        // imposta il padding su tutti i lati a 0 pixel
        bottomNavigationView.setPadding(0, 0 , 0, 0)

        //si setta a null l'ascoltatore per le modifiche
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        // indica che l'etichetta sarà visibile solo quando il relativo menu sarà selezionato
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView.background = null

        /*
        Questa parte permette all'utente di tornare indietro alla pagina principale
        cliccando sul bottone per tornare indietro nella parte alta dello schermo
         */
        val backButton = findViewById<ImageButton>(binding.backDHButtonPracticeSession.id)
        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        val seePracticeSessionFragment = SeePracticeSessionFragment()

        // Inizia una transazione del FragmentManager per effettuare una modifica
        // al layout corrente
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameDHPracticeSession.id, seePracticeSessionFragment).commit()
        }
    }

    /*
        This method is used to set the status bar
        completely transparent but keeping the icon at the top
        of the layout
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
}