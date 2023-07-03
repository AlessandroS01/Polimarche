package com.example.polimarche.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentLoginBinding
import com.example.polimarche.users.all.menu.main.HomeFragment
import com.example.polimarche.users.all.menu.main.MainActivity
import java.security.MessageDigest
import com.google.firebase.auth.FirebaseAuth

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

        val firebaseAuth = FirebaseAuth.getInstance()

        val signUpFragment = SignUpFragment()
        binding.signUpFromLogin.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayoutLoginSignIn, signUpFragment).commit()
            }
        }
        val errorMessageTextView = binding.errorMessageTextView


        binding.signInButton.setOnClickListener {

            if ( binding.MatricolaInput.text?.isNotEmpty()!!
                &&
                    binding.PasswordInput.text?.isNotEmpty()!!
            )
            {
                /*
            This section acquire the text written in
            the 2 different boxes of activity_login_interface
            that will be compared with the values saved inside the database
             */
            val matricola: String = binding.MatricolaInput.text.toString()
            val matriculation = "s$matricola@studenti.univpm.it"
            val password: String = binding.PasswordInput.text.toString()

                /* val passwordEncrypted = enctyptSha256(password) */
                /*
                 TODO: CREARE LA CONNESSIONE AL DATABASE PER CONFRONTARE I MEMBRI
                 TODO: E DETERMINARE SE SI E' LOGGATO UN CAPOREPARTO O UN RESPONSABILE
                 */
                // Effettua il login con Firebase Authentication
            firebaseAuth.signInWithEmailAndPassword(matriculation, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Avvia MainActivity
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.putExtra("EXTRA_MATRICULATION", matriculation)
                            intent.putExtra("EXTRA_PASSWORD", password)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            // Login fallito
                            val errorMessage = "Login fallito. Riprova."
                            errorMessageTextView.text = errorMessage
                            errorMessageTextView.visibility = View.VISIBLE
                        }
                }
            }
            else{
                val errorMessage = "Inserire le credenziali"
                errorMessageTextView.text = errorMessage
                errorMessageTextView.visibility = View.VISIBLE
            }


        }

        binding.passwordRecoveryFromLogin.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            val matricola: String = binding.MatricolaInput.text.toString()
            val matriculation = "s$matricola@studenti.univpm.it"

            auth.sendPasswordResetEmail(matriculation)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Email di recupero password inviata con successo
                        Toast.makeText(requireContext(), "Email di recupero password inviata", Toast.LENGTH_SHORT).show()
                    } else {
                        // Gestione dell'errore durante l'invio dell'email di recupero password
                        val errorMessage = task.exception?.message
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
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