package com.example.polimarche.users.department_head.menu.practice_session

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileprogramming.databinding.ActivityDepartmentHeadsPracticeSessionBinding
import com.example.polimarche.users.all.menu.practice_session.SeePracticeSessionFragment

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