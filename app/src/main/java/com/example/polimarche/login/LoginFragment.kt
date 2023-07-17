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

    // Variabili utilizzate per eseguire il binding degli elementi del layout fragment_login utilizzando FragmentLoginBinding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // Creazione vista associata al layout, viene eseguito il binding del layout fragment_login utilizzando FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // La funzione inflate prende il layout XML del frammento (fragment_login) e lo converte in un oggetto FragmentLoginBinding
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // savedInstanceState è oggetto Bundle che contiene i dati salvati dal precedente stato del fragment
        // super si riferisce alla superclasse LoginFragment
        val firebaseAuth = FirebaseAuth.getInstance() // Viene ottenuta un'istanza di FirebaseAuth

        val signUpFragment = SignUpFragment() // Viene creato un'istanza di SignUpFragment

        // Gestione del click sul pulsante di registrazione
        binding.signUpFromLogin.setOnClickListener {
            // Viene eseguita una transazione del FragmentManager per sostituire il contenuto
            // del frameLayoutLoginSignIn con il signUpFragment
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayoutLoginSignIn, signUpFragment).commit()
            }
        }
        val errorMessageTextView = binding.errorMessageTextView // Referenza all'elemento errorMessageTextView


        binding.signInButton.setOnClickListener {
            // Controllo che i campi di matricola e password siano compilati
            if ( binding.MatricolaInput.text?.isNotEmpty()!! && binding.PasswordInput.text?.isNotEmpty()!!) {

            val matricola: String = binding.MatricolaInput.text.toString() // estrae il testo inserito nel campo di input
            val matriculation = "s$matricola@studenti.univpm.it"
            val password: String = binding.PasswordInput.text.toString()

                // Effettua il login utilizzando il metodo signInWithEmailAndPassword di FirebaseAuth
            firebaseAuth.signInWithEmailAndPassword(matriculation, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) { // Se il login ha successo
                            val intent = Intent(requireContext(), MainActivity::class.java) // Creazione oggetto Intent per avviare l'MainActivity
                            // matriculation e password vengono inserite come dati extra nell'intent
                            intent.putExtra("EXTRA_MATRICULATION", matriculation)
                            intent.putExtra("EXTRA_PASSWORD", password)
                            startActivity(intent) // Avvio MainActivity
                            requireActivity().finish() // Activity corrente viene chiusa
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
            // Controllo sul campo di input della matricola
            if ( binding.MatricolaInput.text?.isEmpty()!! ){
                Toast.makeText(requireContext(), "Matricola is required", Toast.LENGTH_SHORT).show()
            }
            else{
                val auth = FirebaseAuth.getInstance() // istanza di FirebaseAuth
                val matricola: String = binding.MatricolaInput.text.toString()
                val matriculation = "s$matricola@studenti.univpm.it"

                // Metodo sendPasswordResetEmail di FirebaseAuth
                auth.sendPasswordResetEmail(matriculation)
                    .addOnCompleteListener { task ->
                        // Email di recupero password inviata con successo
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Email di recupero password inviata", Toast.LENGTH_SHORT).show()
                        } else {
                            // Errore durante l'invio dell'email di recupero password
                            Toast.makeText(requireContext(), "Email di recupero password NON inviata", Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }
    }

}