package com.example.polimarche.Managers

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.polimarche.Managers.Menu.MenuHomeFragment
import com.example.polimarche.Managers.Menu.MenuTeamFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ManagersMain: AppCompatActivity() {

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_main)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        val matriculation = intent.getStringExtra("EXTRA_MATRICULATION").toString()
        val password = intent.getStringExtra("EXTRA_PASSWORD").toString()

        /*
        This part of the code deletes the background shadow
        created by the bottomNavigationView
         */
        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigation.background = null
        navigation.menu.getItem(1).isEnabled = false

        /*
        Part that allow us to change the Fragments inside
        R.id.fragmentLayoutManagers at the touch of the buttons
        right below the bottomNavigationBar
         */
        val homeFragment = MenuHomeFragment()
        val teamFragment = MenuTeamFragment()
        setCurrentFragment(homeFragment)
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.team_members -> setCurrentFragment(teamFragment)
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
        FrameLayout used in the MainActivityManagers directly
        without the use of the methods provided by the class
        Fragment.
     */
    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentLayoutManagers, fragment).commit()
        }
    }
}
