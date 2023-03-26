package com.example.mobileprogramming

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
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
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)


        val matriculation = intent.getStringExtra("EXTRA_MATRICULATION").toString()
        val password = intent.getStringExtra("EXTRA_PASSWORD").toString()

        val fragment = MenuFragment()
        val menuButton = findViewById<ImageButton>(R.id.menuButtonManager)
        menuButton.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.popMenu, fragment)
                commit()
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