package com.example.polimarche.users.department_head.menu.setup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.polimarche.R
import com.example.polimarche.databinding.ActivityDepartmentHeadsSetupBinding
import com.example.polimarche.users.all.menu.setup.problem.ProblemsSetupFragment
import com.example.polimarche.users.all.menu.setup.see.SeeSetupFragment
import com.example.polimarche.users.all.menu.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class DepartmentHeadsSetupActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDepartmentHeadsSetupBinding

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentHeadsSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val bottomNavigationView = findViewById<BottomNavigationView>(binding.setupDHBottomNavigationView.id)
        bottomNavigationView.setPadding(0, 0 , 0, 0)
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView.background = null

        /*
        This part allows the user to go back at the main page
        clicking on the back button at the top of the screen
         */
        val backButton = findViewById<ImageButton>(binding.backDHButtonSetup.id)
        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }


        val seeSetupsFragment = SeeSetupFragment(window)
        val problemSetupFragment = ProblemsSetupFragment(window)
        setCurrentFragment(seeSetupsFragment)
        binding.setupDHBottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.see_setup_dh -> setCurrentFragment(seeSetupsFragment)
                R.id.problems_setup_dh -> setCurrentFragment(problemSetupFragment)
            }


            true
        }



    }

    /*
        This method is used to change the View inside the
        FrameLayout used in the "activity_department_heads_setup_binding" directly
        without the use of the methods provided by the class
        Fragment.
     */
    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameDHSetup.id, fragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
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