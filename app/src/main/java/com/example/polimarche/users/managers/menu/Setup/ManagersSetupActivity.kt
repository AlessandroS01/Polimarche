package com.example.polimarche.users.managers.menu.setup

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
import com.example.polimarche.users.all.menu.setup.problem.ProblemsSetupFragment
import com.example.polimarche.users.all.menu.setup.see.SeeSetupFragment
import com.example.polimarche.users.managers.menu.setup.delete.DeleteSetupFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class ManagersSetupActivity : AppCompatActivity(){

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_setup)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.setupManagersBottomNavigationView)
        bottomNavigationView.setPadding(0, 0 , 0, 0)
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView.background = null

        /*
        This part allows the user to go back at the main page
        clicking on the back button at the top of the screen
         */
        val backButton = findViewById<ImageButton>(R.id.backButtonSetup)
        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        /*
        Parte che ci permette di cambiare i Fragment dentro R.id.frameSetupManagers al tocco
        del bottone nella bottomNavigationBar
         */
        val seeSetupFragment = SeeSetupFragment(window)
        //val createSetupFragment = CreateSetupFragment(window)
        val problemsSetupFragment = ProblemsSetupFragment(window)
        val deleteSetupFragment = DeleteSetupFragment(window)
        setCurrentFragment(seeSetupFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.see_setup -> setCurrentFragment(seeSetupFragment)
                //R.id.create_setup -> setCurrentFragment(createSetupFragment)
                R.id.delete_setup -> setCurrentFragment(deleteSetupFragment)
                R.id.problems_setup -> setCurrentFragment(problemsSetupFragment)
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
        This method is used to change the View inside the
        FrameLayout used in the "activity_managers_setup" directly
        without the use of the methods provided by the class
        Fragment.
     */
    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameSetupManagers, fragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}