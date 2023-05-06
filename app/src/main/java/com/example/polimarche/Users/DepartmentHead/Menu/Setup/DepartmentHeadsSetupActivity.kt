package com.example.polimarche.Users.DepartmentHead.Menu.Setup

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.ActivityDepartmentHeadsSetupBinding
import com.example.polimarche.Users.All.Menu.Setup.Problem.ProblemsSetupFragment
import com.example.polimarche.Users.All.Menu.Setup.See.SeeSetupFragment

class DepartmentHeadsSetupActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDepartmentHeadsSetupBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityDepartmentHeadsSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val seeSetupsFragment = SeeSetupFragment()
        val problemSetupFragment = ProblemsSetupFragment()
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
}