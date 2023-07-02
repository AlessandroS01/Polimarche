package com.example.polimarche.users.department_head.menu.tracks

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.R
import com.example.polimarche.databinding.ActivityDepartmentHeadsTracksBinding
import com.example.polimarche.users.all.menu.main.MainActivity
import com.example.polimarche.users.all.menu.tracks.SeeTracksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class DepartmentHeadsTracksActivity: AppCompatActivity() {

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    private lateinit var binding: ActivityDepartmentHeadsTracksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentHeadsTracksBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_department_heads_tracks)

        val bottomNavigationView = findViewById<BottomNavigationView>(binding.tracksDHBottomNavigationView.id)
        bottomNavigationView.setPadding(0, 0 , 0, 0)
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView.background = null

        /*
        This part allows the user to go back at the main page
        clicking on the back button at the top of the screen
         */
        val backButton = findViewById<ImageButton>(binding.dhBackButtonTracks.id)
        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        val seeTracksFragment = SeeTracksFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameDHTracksManagers.id, seeTracksFragment).commit()
        }
    }
}