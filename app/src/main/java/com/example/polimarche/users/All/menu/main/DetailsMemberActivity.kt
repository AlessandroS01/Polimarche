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

    // Variabile utilizzata per effettuare il binding dei componenti del file di layout XML
    private lateinit var binding : ActivityGeneralDetailsMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Invocazione dell'implementazione del metodo onCreate() nella classe genitore AppCompatActivity
        binding = ActivityGeneralDetailsMemberBinding.inflate(layoutInflater) // Inflating
        setContentView(binding.root) // Imposta il layout dell'attività

        // Istruzioni per rendere trasparente la barra di stato
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        // Istruzioni per impostare i valori passati tramite l'intent, nelle varie componenti di layout
        binding.textViewCN.text = intent.getStringExtra("NUMBER")
        binding.textViewDOB.text = intent.getStringExtra("DOB")
        binding.textViewEmail.text = intent.getStringExtra("EMAIL")
        binding.textViewFN.text = intent.getStringExtra("FIRST_NAME")
        binding.textViewLN.text = intent.getStringExtra("LAST_NAME")
        binding.textViewWorkshop.text = intent.getStringExtra("WORKSHOP_AREA")
        binding.textViewMNumber.text = intent.getLongExtra("MATRICULATION", 0).toString()


        val backButton : ImageButton = findViewById(R.id.backButtonDetailMember)
        // Quando il bottone viene cliccato si chiude l'activity corrente
        backButton.setOnClickListener {
            finish()
        }
    }

    /*
        Questo metodo viene utilizzato per impostare la barra di stato
        completamente trasparente
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