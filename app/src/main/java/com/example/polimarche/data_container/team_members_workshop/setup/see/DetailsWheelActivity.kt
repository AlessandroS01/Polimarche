package com.example.polimarche.data_container.team_members_workshop.setup.see

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.databinding.ActivityGeneralDetailsWheelBinding
import com.example.polimarche.data_container.setup.SetupViewModel
import com.example.polimarche.data_container.wheel.DataWheel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class DetailsWheelActivity: AppCompatActivity() {

    private val setupViewModel: SetupViewModel by viewModels()

    private lateinit var wheelDetails : DataWheel

    private lateinit var binding : ActivityGeneralDetailsWheelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FirebaseApp.initializeApp(this)
        binding = ActivityGeneralDetailsWheelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        //val db = FirebaseFirestore.getInstance()
        val setupCode = intent.getIntExtra("SETUP_CODE", -1)
        val wheelPosition = intent.getStringExtra("WHEEL_POSITION")


        // set data inside the layout
        setupViewModel.setupList.observe(this) {
            val setup = setupViewModel.setupList.value?.filter { it.code == setupCode  }?.get(0)!!

            when(wheelPosition){
                "Front right" -> {
                    wheelDetails = setup.frontRightWheel
                }
                "Front left" -> {
                    wheelDetails = setup.frontLeftWheel
                }
                "Rear right" -> {
                    wheelDetails = setup.rearRightWheel
                }
                "Rear left" -> {
                    wheelDetails = setup.rearLeftWheel
                }
            }

            binding.wheelCodeDetailsWheel.text = "Wheel code: ${wheelDetails.code}"
            Log.d("wheelDetails","wheelDetails:$wheelDetails")
            binding.codificationDetailsWheel.text = wheelDetails.codification

            binding.positionDetailsWheel.text = wheelPosition

            binding.pressureDetailsWheel.text = wheelDetails.pressure

            binding.camberDetailsWheel.text = wheelDetails.camber

            binding.toeDetailsWheel.text = wheelDetails.toe
        }

        binding.backButtonDetailWheel.setOnClickListener {
            finish()
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