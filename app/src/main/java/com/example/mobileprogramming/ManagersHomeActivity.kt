package com.example.mobileprogramming

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageButton
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

        /*
            Code that allows the users to pop up and close
            the menu with the same button .
            The variable showedMenu is used to track whether
            the menu is already popped up in order to hide and show
            the menu properly.
         */
        val menuFragment = MenuFragment()
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_menu)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_menu)
        var showedMenu = false
        val menuButton = findViewById<ImageButton>(R.id.menuButtonManager)
        menuButton.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                showedMenu = if (!showedMenu) {
                    replace(R.id.popMenu, menuFragment).commit()
                    menuButton.setImageResource(R.drawable.close_md_svgrepo_com)
                    menuButton.startAnimation(fadeOut)
                    true
                } else {
                    supportFragmentManager.beginTransaction().remove(menuFragment).commit()
                    menuButton.setImageResource(R.drawable.menu_hamburger_svgrepo_com)
                    menuButton.startAnimation(fadeIn)
                    false
                }
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