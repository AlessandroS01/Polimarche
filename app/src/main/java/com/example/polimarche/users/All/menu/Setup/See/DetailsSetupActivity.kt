package com.example.polimarche.users.all.menu.setup.see

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.databinding.ActivityGeneralDetailsSetupBinding
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel

class DetailsSetupActivity: AppCompatActivity() {

    private val setupViewModel: SetupViewModel by viewModels()

    private lateinit var setup: DataSetup

    private lateinit var binding : ActivityGeneralDetailsSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralDetailsSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val setupCode = intent.getIntExtra("SETUP_CODE", -1)

        if(setupCode != -1){
            // Entire setup data found from the setup code
            setup = setupViewModel.setupList.filter { it.code == setupCode }[0]

            binding.setupCodeSeeSetup.text = "Setup code: ${setup.code}"

            binding.frontRightWheelCodeSeeSetup.text = setup.frontRightWheel.code.toString()
            binding.frontLeftWheelCodeSeeSetup.text = setup.frontLeftWheel.code.toString()
            binding.rearRightWheelCodeSeeSetup.text = setup.rearRightWheel.code.toString()
            binding.rearLeftWheelCodeSeeSetup.text = setup.rearLeftWheel.code.toString()

            binding.frontEndBalanceBrake.text = setup.frontBalance.brake.toString()
            binding.frontEndBalanceWeight.text = setup.frontBalance.weight.toString()

            binding.backEndBalanceBrake.text = setup.backBalance.brake.toString()
            binding.backEndBalanceWeight.text = setup.backBalance.weight.toString()

            binding.frontWingHoleSeeSetup.text = setup.frontWingHole

            binding.frontDamperCodeSeeSetup.text = setup.frontDamper.code.toString()
            binding.backDamperCodeSeeSetup.text = setup.backDamper.code.toString()

            binding.frontSpringCodeSeeSetup.text = setup.frontSpring.code.toString()
            binding.backSpringCodeSeeSetup.text = setup.backSpring.code.toString()

            binding.eventPreferredSeeSetup.text = setup.preferredEvent

            var notes = ""

            setup.notes.forEachIndexed { index, s ->
                notes += if(index == setup.notes.size - 1 ) s else "${s}\n"
            }

            binding.notesSeeSetup.text = notes

        }

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
                this.putExtra("WHEEL_POSITION", "Front right")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        frontLeftWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                this.putExtra("WHEEL_POSITION", "Front left")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        rearRightWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                this.putExtra("WHEEL_POSITION", "Rear right")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        rearLeftWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                this.putExtra("WHEEL_POSITION", "Rear left")
                this.putExtra("SETUP_CODE", setup.code)
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
                this.putExtra("DAMPER_POSITION", "Front")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        backDamper.setOnClickListener {
            Intent(this, DetailsDamperActivity::class.java).apply {
                this.putExtra("DAMPER_POSITION", "Back")
                this.putExtra("SETUP_CODE", setup.code)
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
                this.putExtra("SPRING_POSITION", "Front")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        backSpring.setOnClickListener {
            Intent(this, DetailsSpringActivity::class.java).apply {
                this.putExtra("SPRING_POSITION", "Back")
                this.putExtra("SETUP_CODE", setup.code)
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