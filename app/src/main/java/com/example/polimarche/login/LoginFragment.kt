package com.example.polimarche.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentLoginBinding
import com.example.polimarche.users.all.menu.main.MainActivity
import java.security.MessageDigest

class LoginFragment: Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpFragment = SignUpFragment()
        binding.signUpFromLogin.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayoutLoginSignIn, signUpFragment).commit()
            }
        }

        binding.signInButton.setOnClickListener {
            /*
            This section acquire the text written in
            the 2 different boxes of the activity_login_interface
            that, inside the OnClickListener method of the button,
            will compare with the values saved inside the database
             */
            val matriculation: String = binding.MatricolaInput.text.toString()
            val password: String = binding.PasswordInput.text.toString()

            val passwordEncrypted = enctyptSha256(password)
            /*
             TODO: CREARE LA CONNESSIONE AL DATABASE PER CONFRONTARE I MEMBRI
             TODO: E DETERMINARE SE SI E' LOGGATO UN CAPOREPARTO O UN RESPONSABILE
             */
            Intent(this.context, MainActivity::class.java).also {
                it.putExtra("EXTRA_MATRICULATION", matriculation)
                it.putExtra("EXTRA_PASSWORD", passwordEncrypted)
                startActivity(it)
            }

        }
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