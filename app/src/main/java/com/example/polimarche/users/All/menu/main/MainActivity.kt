package com.example.polimarche.users.all.menu.main

import android.app.Dialog
import android.content.Intent
import android.view.Gravity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.polimarche.R
import com.example.polimarche.login.LoginActivity
import com.example.polimarche.users.department_head.menu.practice_session.DepartmentHeadsPracticeSessionActivity
import com.example.polimarche.users.department_head.menu.setup.DepartmentHeadsSetupActivity
import com.example.polimarche.users.department_head.menu.tracks.DepartmentHeadsTracksActivity
import com.example.polimarche.users.managers.menu.practice_session.ManagersPracticeSessionActivity
import com.example.polimarche.users.managers.menu.setup.ManagersSetupActivity
import com.example.polimarche.users.managers.menu.tracks.ManagersTracksActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_SELECTED
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity: AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // savedInstanceState è oggetto Bundle (contenitore di dati) che
        // contiene i dati salvati dal precedente stato dell'activity
        // super si riferisce alla superclasse AppCompatActivity
        setContentView(R.layout.activity_general_main)  // metodo imposta il layout dell'activity specificando il file XML activity_login
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
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        // imposta il padding su tutti i lati a 0 pixel
        bottomNavigationView.setPadding(0, 0, 0, 0)

        //si setta a null l'ascoltatore per le modifiche
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.background = null  // imposta lo sfondo su null
        // indica che l'etichetta sarà visibile solo quando il relativo menu sarà selezionato
        bottomNavigationView.labelVisibilityMode = LABEL_VISIBILITY_SELECTED
        // disabilita il secondo elemento (item in posizione 1) del menu dell'oggetto
        bottomNavigationView.menu.getItem(1).isEnabled = false

        /*
        Parte di codice che ci permette di cambiare i fragment all'interno
        R.id.fragmentLayoutManagers con il tocco dei pulsanti
        nella bottomNavigationBar
         */
        val homeFragment = HomeFragment()
        val teamFragment = TeamFragment()
        setCurrentFragment(homeFragment)

        // listener per la selezione degli elementi nella bottomNavBar
        // Il codice all'interno delle parentesi verrà eseguito quando un elemento del menu viene
        // selezionato
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                // quando l'ID dell'elemento selezionato corrisponde a R.id.home si setta
                // come fragment homeFragment
                R.id.home -> setCurrentFragment(homeFragment)
                // quando l'ID dell'elemento selezionato corrisponde a R.id.team_members si setta
                // come fragment teamFragment
                R.id.team_members -> setCurrentFragment(teamFragment)
            }
            true
        }



        // Parte di codice che crea il menu dopo il clic sul floating button.

        // riferimento a un elemento FloatingActionButton nel layout corrente utilizzando il suo ID
        val floatingMenuButton: FloatingActionButton = findViewById(R.id.floatingButton)
        val dialog = Dialog(this) // dichiarata una variabile di tipo Dialog passando this
        // come contesto
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // rimuovere la barra del titolo
        // predefinita
        // si imposta il layout da utilizzare per il contenuto del Dialog
        dialog.setContentView(R.layout.dialog_box_general_main_floating_menu)
        // Di seguito si ottengono dei riferimenti a i vari LinearLayout all'interno del Dialog
        // utilizzando gli ID
        val homeLayout = dialog.findViewById<LinearLayout>(R.id.layoutHome)
        val teamLayout = dialog.findViewById<LinearLayout>(R.id.layoutTeam)
        val setupLayout = dialog.findViewById<LinearLayout>(R.id.layoutSetup)
        val tracksLayout = dialog.findViewById<LinearLayout>(R.id.layoutTracks)
        val practiceSessionLayout = dialog.findViewById<LinearLayout>(R.id.layoutPracticeSession)
        val logOutLayout = dialog.findViewById<LinearLayout>(R.id.layoutLogOut)

        // viene ottenuto l'utente corrente tramite il FirebaseAuth e assegnato a currentUser
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        // Viene ottenuto l'ID dell'utente corrente utilizzando la proprietà uid di currentUser
        val userId: String = currentUser?.uid!!
        val db = FirebaseFirestore.getInstance()

        // avvia una coroutine nell'ambito del thread di I/O per eseguire operazioni asincrone
        // senza bloccare il thread principale dell'interfaccia utente
        CoroutineScope(Dispatchers.IO).launch {
            // Viene effettuata una query al documento corrispondente all'utente corrente
            // nel database.
            // Se il documento esiste, il codice all'interno del blocco di istruzioni verrà eseguito.
            db.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Si ottiene il valore del campo "role"
                        val role = documentSnapshot.getString("role").toString()
                        floatingMenuButton.setOnClickListener {
                                    /*
                                    Definisce le proprietà del menu
                                     */
                                    dialog.show()
                                    dialog.window?.setLayout(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                    )
                                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    dialog.window?.attributes?.windowAnimations =
                                        R.style.DialogAnimation
                                    dialog.window?.setGravity(Gravity.BOTTOM)
                                    homeLayout.setOnClickListener {
                                            dialog.hide()
                                            val itemHome = R.id.home
                                            setCurrentFragment(homeFragment)
                                        // seleziona l'elemento del menu "home" nel
                                        // BottomNavigationView tramite la proprietà selectedItemId
                                            bottomNavigationView.selectedItemId = itemHome
                                        }
                                        teamLayout.setOnClickListener {
                                            dialog.hide()
                                            val itemTeam = R.id.team_members
                                            setCurrentFragment(teamFragment)
                                            bottomNavigationView.selectedItemId = itemTeam
                                        }
                                        logOutLayout.setOnClickListener {
                                                dialog.hide()
                                                Intent(it.context, LoginActivity::class.java).also {
                                                    startActivity(it)
                                                }
                                            }
                                }
                        when(role) {
                            // in base al valore del campo "role" estratto dal db e in base al
                            // bottone cliccato, viene nascosto il menù e
                            // vengono aperte le activity corrispondenti al valore stesso
                            "Manager" -> {
                                setupLayout.setOnClickListener {
                                    dialog.hide()
                                    Intent(it.context, ManagersSetupActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                                tracksLayout.setOnClickListener {
                                    dialog.hide()
                                    Intent(it.context, ManagersTracksActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                                practiceSessionLayout.setOnClickListener {
                                    dialog.hide()
                                    Intent(it.context, ManagersPracticeSessionActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                            }
                            "Department head"->{
                                setupLayout.setOnClickListener {
                                    dialog.hide()
                                    Intent(it.context, DepartmentHeadsSetupActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                                tracksLayout.setOnClickListener {
                                    dialog.hide()
                                    Intent(it.context, DepartmentHeadsTracksActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                                practiceSessionLayout.setOnClickListener {
                                    dialog.hide()
                                    Intent(it.context, DepartmentHeadsPracticeSessionActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                            }
                        }
                    }
                }
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

            private fun setCurrentFragment(fragment: Fragment) {
                // Inizia una transazione del FragmentManager per effettuare una modifica
                // al layout corrente
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameMainUsers, fragment).commit() // sostituisce il contenuto
                    // del frameMainUsers con l'istanza di fragment passata come parametro
                    setReorderingAllowed(true) // riordinamento delle transazioni
                    addToBackStack(null) // aggiungere la transazione corrente nello
                                               // stack di transazioni all'indietro
                }
            }

    // Questa funzione sposta l'attività in background anziché terminarla immediatamente
            override fun onBackPressed() {
                moveTaskToBack(false) // False indica che l'attività non deve essere terminata
            }


        }

