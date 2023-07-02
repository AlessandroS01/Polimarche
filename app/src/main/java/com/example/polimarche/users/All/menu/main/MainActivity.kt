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
import com.example.polimarche.users.managers.menu.practice_session.ManagersPracticeSessionActivity
import com.example.polimarche.users.managers.menu.setup.ManagersSetupActivity
import com.example.polimarche.users.managers.menu.tracks.ManagersTracksActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_SELECTED

class MainActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_main)
        /*
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }

         */

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        /*
        This part of the code deletes the background shadow
        created by the bottomNavigationView
         */
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        bottomNavigationView.setPadding(0, 0 , 0, 0)
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.background = null
        bottomNavigationView.labelVisibilityMode = LABEL_VISIBILITY_SELECTED
        bottomNavigationView.menu.getItem(1).isEnabled = false

        /*
        Part that allow us to change the Fragments inside
        R.id.fragmentLayoutManagers at the touch of the buttons
        right below the bottomNavigationBar
         */
        val homeFragment = HomeFragment()
        val teamFragment = TeamFragment()
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
        dialog.setContentView(R.layout.dialog_box_general_main_floating_menu)
        val homeLayout = dialog.findViewById<LinearLayout>(R.id.layoutHome)
        val teamLayout = dialog.findViewById<LinearLayout>(R.id.layoutTeam)
        val setupLayout = dialog.findViewById<LinearLayout>(R.id.layoutSetup)
        val tracksLayout = dialog.findViewById<LinearLayout>(R.id.layoutTracks)
        val practiceSessionLayout = dialog.findViewById<LinearLayout>(R.id.layoutPracticeSession)
        val logOutLayout = dialog.findViewById<LinearLayout>(R.id.layoutLogOut)
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
            Intent(this, ManagersSetupActivity::class.java).also {
                startActivity(it)
            }
        }
        tracksLayout.setOnClickListener {
            dialog.hide()

            Intent(this, ManagersTracksActivity::class.java).also {
                startActivity(it)
            }
        }
        practiceSessionLayout.setOnClickListener {
            dialog.hide()
            Intent(this, ManagersPracticeSessionActivity::class.java).also {
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
            replace(R.id.frameMainUsers, fragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onBackPressed(){
        moveTaskToBack(false);
    }


}
