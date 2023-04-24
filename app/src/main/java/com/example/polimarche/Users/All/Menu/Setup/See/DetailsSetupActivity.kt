package com.example.polimarche.Users.All.Menu.Setup.See

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileprogramming.databinding.ActivityGeneralDetailsSetupBinding

class DetailsSetupActivity: AppCompatActivity() {

    private lateinit var binding : ActivityGeneralDetailsSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralDetailsSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)


        val backButton : ImageButton = binding.backButtonDetailSetup
        backButton.setOnClickListener {
            finish()
        }

        /*
        Let the user navigate from the details of a setup to
        the particular frame listing all the details of the single
        wheel.
         */
        val frontRightWheel: ImageView = binding.frontRightWheelDetails
        val frontLeftWheel: ImageView = binding.frontLeftWheelDetails
        val rearRightWheel: ImageView = binding.rearRightWheelDetails
        val rearLeftWheel: ImageView = binding.rearLeftWheelDetails
        frontRightWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                startActivity(this)
            }
        }
        frontLeftWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                startActivity(this)
            }
        }
        rearRightWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                startActivity(this)
            }
        }
        rearLeftWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                startActivity(this)
            }
        }

        /*
        Let the user navigate from the details of a setup to
        the particular frame listing all the details of the single
        damper.
         */
        val frontDamper: ImageView = binding.frontDamperDetails
        val backDamper: ImageView = binding.backDamperDetails
        frontDamper.setOnClickListener {
            Intent(this, DetailsDamperActivity::class.java).apply {
                startActivity(this)
            }
        }
        backDamper.setOnClickListener {
            Intent(this, DetailsDamperActivity::class.java).apply {
                startActivity(this)
            }
        }

        /*
        Let the user navigate from the details of a setup to
        the particular frame listing all the details of the single
        spring.
         */
        val frontSpring: ImageView = binding.frontSpringDetails
        val backSpring: ImageView = binding.backSpringDetails
        frontSpring.setOnClickListener {
            Intent(this, DetailsSpringActivity::class.java).apply {
                startActivity(this)
            }
        }
        backSpring.setOnClickListener {
            Intent(this, DetailsSpringActivity::class.java).apply {
                startActivity(this)
            }
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