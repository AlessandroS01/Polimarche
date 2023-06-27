package com.example.polimarche.users.all.menu.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.R
import com.example.polimarche.databinding.ActivityGeneralDetailsMemberBinding

class DetailsMemberActivity: AppCompatActivity() {

    private lateinit var binding : ActivityGeneralDetailsMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralDetailsMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        binding.textViewCN.text = intent.getStringExtra("NUMBER")
        binding.textViewDOB.text = intent.getStringExtra("DOB")
        binding.textViewEmail.text = intent.getStringExtra("EMAIL")
        binding.textViewFN.text = intent.getStringExtra("FIRST_NAME")
        binding.textViewLN.text = intent.getStringExtra("LAST_NAME")
        binding.textViewWorkshop.text = intent.getStringExtra("WORKSHOP_AREA")
        binding.textViewMNumber.text = intent.getLongExtra("MATRICULATION", 0).toString()



        val backButton : ImageButton = findViewById(R.id.backButtonDetailMember)
        backButton.setOnClickListener {
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