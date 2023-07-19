package com.example.polimarche.users.managers.menu.practice_session

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.polimarche.R
import com.example.polimarche.users.all.menu.main.MainActivity
import com.example.polimarche.users.all.menu.practice_session.SeePracticeSessionFragment
import com.example.polimarche.users.managers.menu.practice_session.Create.AddPracticeSessionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class ManagersPracticeSessionActivity : AppCompatActivity(){

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_practice_session)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.practiceSessionBottomNavigationView)
        bottomNavigationView.setPadding(0, 0 , 0, 0)
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView.background = null

        /*
        Questa parte permette all'utente di tornare alla pagina principale
        facendo clic sul pulsante Indietro nella parte superiore dello schermo.
         */
        val backButton = findViewById<ImageButton>(R.id.backButtonPracticeSession)
        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }


        /*
        Parte che ci permette di cambiare i frammenti all'interno
        R.id.framePracticeSessionManager al tocco dei pulsanti
        proprio sotto il bottomNavigationBar
        */
        val seePracticeSessionFragment = SeePracticeSessionFragment()
        val addPracticeSessionFragment = AddPracticeSessionFragment()
        setCurrentFragment(seePracticeSessionFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.see_practice_session -> setCurrentFragment(seePracticeSessionFragment)
                R.id.add_practice_session -> setCurrentFragment(addPracticeSessionFragment)
            }
            true
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

    /*
        Questo metodo viene utilizzato per modificare la vista all'interno del file
        FrameLayout utilizzato direttamente in "activity_managers_practice_session".
        senza l'uso dei metodi forniti dalla classe Fragment.
     */
    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framePracticeSessionManagers, fragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}