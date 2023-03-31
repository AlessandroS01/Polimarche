package com.example.polimarche.Managers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.polimarche.Managers.Menu.Setup.MenuCreateSetupFragment
import com.example.polimarche.Managers.Menu.Setup.MenuDeleteSetupFragment
import com.example.polimarche.Managers.Menu.Setup.MenuProblemsSetupFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.polimarche.Managers.Menu.Setup.MenuSeeSetupFragment

class ManagersSetup : AppCompatActivity(){

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_setup)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.setupBottomNavigationView)
        bottomNavigationView.background = null

        /*
        This part allows the user to go back at the main page
        clicking on the back button at the top of the screen
         */
        val backButton = findViewById<ImageButton>(R.id.backButtonSetup)
        backButton.setOnClickListener {
            Intent(this, ManagersMain::class.java).also {
                startActivity(it)
            }
        }


        /*
        Part that allow us to change the Fragments inside
        R.id.frameSetupManagers at the touch of the buttons
        right below the bottomNavigationBar
         */
        val seeSetupFragment = MenuSeeSetupFragment()
        val createSetupFragment = MenuCreateSetupFragment()
        val problemsSetupFragment = MenuProblemsSetupFragment()
        val deleteSetupFragment = MenuDeleteSetupFragment()
        setCurrentFragment(seeSetupFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.see_setup -> setCurrentFragment(seeSetupFragment)
                R.id.create_setup -> setCurrentFragment(createSetupFragment)
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