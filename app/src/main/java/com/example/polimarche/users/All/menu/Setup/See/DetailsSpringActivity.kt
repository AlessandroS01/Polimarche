package com.example.polimarche.users.all.menu.setup.see

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.databinding.ActivityGeneralDetailsSpringBinding
import com.example.polimarche.data_container.setup.SetupViewModel
import com.example.polimarche.data_container.spring.DataSpring

class DetailsSpringActivity: AppCompatActivity() {

    private val setupViewModel: SetupViewModel by viewModels()

    private lateinit var springDetails : DataSpring

    private lateinit var binding : ActivityGeneralDetailsSpringBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralDetailsSpringBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)


        val backButton : ImageButton = binding.backButtonDetailSpring
        backButton.setOnClickListener {
            finish()
        }

        val setupCode = intent.getIntExtra("SETUP_CODE", -1)
        val springPosition = intent.getStringExtra("SPRING_POSITION")
        val setup = setupViewModel.setupList.filter { it.code == setupCode  }[0]

        when(springPosition){
            "Front" ->{
                springDetails = setup.frontSpring
            }
            "Back" ->{
                springDetails = setup.backSpring
            }
        }

        if(setupCode != -1){
            binding.springCodeDetailsSpring.text = "Spring code: ${springDetails.code}"

            binding.codificationDetailsSpring.text = springDetails.codification

            binding.positionDetailsSpring.text = springPosition

            binding.heightDetailsSpring.text = springDetails.height.toString()

            binding.arbPositionDetailsSpring.text = springDetails.arb_position

            binding.arbStiffnessDetailsSpring.text = springDetails.arb_stiffness

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