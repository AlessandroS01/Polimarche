package com.example.polimarche.users.all.menu.setup.see

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.databinding.ActivityGeneralDetailsDamperBinding
import com.example.polimarche.data_container.damper.DataDamper
import com.example.polimarche.data_container.setup.SetupViewModel

class DetailsDamperActivity: AppCompatActivity() {

    private val setupViewModel: SetupViewModel by viewModels()

    private lateinit var damperDetails : DataDamper

    private lateinit var binding : ActivityGeneralDetailsDamperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralDetailsDamperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)


        val backButton : ImageButton = binding.backButtonDetailDamper
        backButton.setOnClickListener {
            finish()
        }

        val setupCode = intent.getIntExtra("SETUP_CODE", -1)
        val damperPosition = intent.getStringExtra("DAMPER_POSITION")
        val setup = setupViewModel.setupList.value?.filter { it.code == setupCode  }?.get(0)

        when(damperPosition){
            "Front" ->{
                if (setup != null) {
                    damperDetails = setup.frontDamper
                }
            }
            "Back" ->{
                if (setup != null) {
                    damperDetails = setup.backDamper
                }
            }
        }

        setupViewModel.setupList.observe(this) { setup ->
            binding.damperCodeDetailsDamper.text = "Damper code: ${damperDetails.code}"

            binding.endDetailsDamper.text = damperPosition

            binding.hsrDetailsDamper.text = damperDetails.hsr.toString()

            binding.lsrDetailsDamper.text = damperDetails.lsr.toString()

            binding.hscDetailsDamper.text = damperDetails.hsc.toString()

            binding.hsrDetailsDamper.text = damperDetails.hsr.toString()
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