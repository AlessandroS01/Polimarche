package com.example.polimarche.users.department_head.menu.practice_session

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.databinding.ActivityDepartmentHeadsPracticeSessionBinding
import com.example.polimarche.users.all.menu.main.MainActivity
import com.example.polimarche.users.all.menu.practice_session.SeePracticeSessionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class DepartmentHeadsPracticeSessionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDepartmentHeadsPracticeSessionBinding

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentHeadsPracticeSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val bottomNavigationView = findViewById<BottomNavigationView>(binding.dHPracticeSessionBottomNavigationView.id)
        bottomNavigationView.setPadding(0, 0 , 0, 0)
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView.background = null

        /*
        This part allows the user to go back at the main page
        clicking on the back button at the top of the screen
         */
        val backButton = findViewById<ImageButton>(binding.backDHButtonPracticeSession.id)
        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        val seePracticeSessionFragment = SeePracticeSessionFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameDHPracticeSession.id, seePracticeSessionFragment).commit()
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