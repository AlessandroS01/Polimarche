package com.example.polimarche.Users.DepartmentHead.Menu.PracticeSession

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileprogramming.databinding.ActivityDepartmentHeadsPracticeSessionBinding
import com.example.polimarche.Users.All.Menu.PracticeSession.SeePracticeSessionFragment

class DepartmentHeadsPracticeSessionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDepartmentHeadsPracticeSessionBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityDepartmentHeadsPracticeSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val seePracticeSessionFragment = SeePracticeSessionFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameDHPracticeSession.id, seePracticeSessionFragment).commit()
        }
    }
}