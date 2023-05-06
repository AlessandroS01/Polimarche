package com.example.polimarche.Users.DepartmentHead.Menu.Tracks

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileprogramming.databinding.ActivityDepartmentHeadsTracksBinding
import com.example.polimarche.Users.All.Menu.Tracks.SeeTracksFragment

class DepartmentHeadsTracksActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDepartmentHeadsTracksBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityDepartmentHeadsTracksBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val seeTracksFragment = SeeTracksFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameDHTracksManagers.id, seeTracksFragment).commit()
        }
    }
}