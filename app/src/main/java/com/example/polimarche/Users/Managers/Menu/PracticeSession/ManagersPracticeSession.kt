package com.example.polimarche.Users.Managers.Menu.PracticeSession

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.polimarche.Users.All.Menu.Main.Main
import com.example.polimarche.Users.All.Menu.PracticeSession.SeePracticeSessionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class ManagersPracticeSession : AppCompatActivity(){

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
        This part allows the user to go back at the main page
        clicking on the back button at the top of the screen
         */
        val backButton = findViewById<ImageButton>(R.id.backButtonPracticeSession)
        backButton.setOnClickListener {
            Intent(this, Main::class.java).also {
                startActivity(it)
            }
        }


        /*
        Part that allow us to change the Fragments inside
        R.id.framePracticeSessionManagers at the touch of the buttons
        right below the bottomNavigationBar
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
        This method is used to change the View inside the
        FrameLayout used in the "activity_managers_practice_session" directly
        without the use of the methods provided by the class
        Fragment.
     */
    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framePracticeSessionManagers, fragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}