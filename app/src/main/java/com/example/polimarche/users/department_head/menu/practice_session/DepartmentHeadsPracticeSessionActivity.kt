package com.example.polimarche.users.department_head.menu.practice_session

import android.content.Intent
import android.os.Bundle
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
}