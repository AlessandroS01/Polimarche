package com.example.mobileprogramming

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManagersHomeActivity : AppCompatActivity() {

    override fun onBackPressed(){
        moveTaskToBack(false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_home)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT



        val matriculation = intent.getStringExtra("EXTRA_MATRICULATION").toString()
        val password = intent.getStringExtra("EXTRA_PASSWORD").toString()

        val testo = findViewById<TextView>(R.id.textViewggggg)
        testo.text = "$matriculation and $password"
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