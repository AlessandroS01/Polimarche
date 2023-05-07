package com.example.polimarche.Users.Managers.Menu.Tracks

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.polimarche.Users.All.Menu.Main.MainActivity
import com.example.polimarche.Users.All.Menu.Tracks.SeeTracksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class ManagersTracksActivity : AppCompatActivity(){

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_tracks)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.tracksBottomNavigationView)
        bottomNavigationView.setPadding(0, 0 , 0, 0)
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView.background = null

        /*
        This part allows the user to go back at the main page
        clicking on the back button at the top of the screen
         */
        val backButton = findViewById<ImageButton>(R.id.backButtonTracks)
        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }


        /*
        Part that allow us to change the Fragments inside
        R.id.frameSetupManagers at the touch of the buttons
        right below the bottomNavigationBar
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
        FrameLayout used in the "activity_managers_tracks" directly
        without the use of the methods provided by the class
        Fragment.
     */
    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameTracksManagers, fragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}