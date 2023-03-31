package com.example.polimarche.Managers

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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.polimarche.LoginActivity
import com.example.polimarche.Managers.Menu.Main.MenuHomeFragment
import com.example.polimarche.Managers.Menu.Main.MenuTeamFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        /*
        Part that allow us to change the Fragments inside
        R.id.fragmentLayoutManagers at the touch of the buttons
        right below the bottomNavigationBar
         */
        val homeFragment = MenuHomeFragment()
        val teamFragment = MenuTeamFragment()
        setCurrentFragment(homeFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.team_members -> setCurrentFragment(teamFragment)
            }
            true
        }


        /*
        Part that creates the menu after the click on the
        floating button.
         */
        val floatingMenuButton : FloatingActionButton = findViewById(R.id.floatingButton)
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.fragment_managers_main_floating_menu)
        val homeLayout = dialog.findViewById<LinearLayout>(R.id.layout_home)
        val teamLayout = dialog.findViewById<LinearLayout>(R.id.layout_team)
        val setupLayout = dialog.findViewById<LinearLayout>(R.id.layout_setup)
        val tracksLayout = dialog.findViewById<LinearLayout>(R.id.layout_tracks)
        val practiceSessionLayout = dialog.findViewById<LinearLayout>(R.id.layout_practice_session)
        val logOutLayout = dialog.findViewById<LinearLayout>(R.id.layout_log_out)
        floatingMenuButton.setOnClickListener {
            /*
            Defines the properties of the menu
             */
            dialog.show()
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            dialog.window?.setGravity(Gravity.BOTTOM)
        }
        homeLayout.setOnClickListener {
            dialog.hide()
            val itemHome = R.id.home
            setCurrentFragment(homeFragment)
            bottomNavigationView.selectedItemId  = itemHome
        }
        teamLayout.setOnClickListener {
            dialog.hide()
            val itemTeam = R.id.team_members
            setCurrentFragment(teamFragment)
            bottomNavigationView.selectedItemId  = itemTeam
        }
        setupLayout.setOnClickListener {
            dialog.hide()
            Intent(this, ManagersSetup::class.java).also {
                startActivity(it)
            }
        }
        tracksLayout.setOnClickListener {
            dialog.hide()
            Intent(this, ManagersTracks::class.java).also {
                startActivity(it)
            }
        }
        practiceSessionLayout.setOnClickListener {
            dialog.hide()
            Intent(this, ManagersPracticeSession::class.java).also {
                startActivity(it)
            }
        }
        logOutLayout.setOnClickListener {
            dialog.hide()
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
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
        FrameLayout used in the "activity_managers_main" directly
        without the use of the methods provided by the class
        Fragment.
     */
    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameMainManagers, fragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}
