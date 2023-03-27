package com.example.mobileprogramming

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_interface)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT

        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        val signIn: ImageButton = findViewById(R.id.signInButton)
        signIn.setOnClickListener {
            /*
            This section acquire the text written in
            the 2 different boxes of the activity_login_interface
            that, inside the OnClickListener method of the button,
            will compare with the values saved inside the database
             */
            val matriculation: String = findViewById<TextInputEditText>(R.id.MatricolaInput).text.toString()
            val password: String = findViewById<TextInputEditText>(R.id.PasswordInput).text.toString()

            val passwordEncrypted = enctyptSha256(password)
            /*
             TODO: CREARE LA CONNESSIONE AL DATABASE PER CONFRONTARE I MEMBRI
             TODO: E DETERMINARE SE SI E' LOGGATO UN CAPOREPARTO O UN RESPONSABILE
             */
            Intent(this, ManagersHomeActivity::class.java).also {
                it.putExtra("EXTRA_MATRICULATION", matriculation)
                it.putExtra("EXTRA_PASSWORD", passwordEncrypted)
                startActivity(it)
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

    /*
        This method return the password used inside
        the login interface encrypted in sha-256
     */
    private fun enctyptSha256(password: String): String {
        val bytes = password.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }


}