package com.example.polimarche.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpFragment: Fragment(R.layout.fragment_sign_up) {

    // Variabili utilizzate per eseguire il binding degli elementi del layout fragment_sign_up utilizzando FragmentSignUpBinding
    private var _binding: FragmentSignUpBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        // La funzione inflate prende il layout XML del frammento (fragment_sign_up) e lo converte in un oggetto FragmentSignUpBinding,
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // savedInstanceState è oggetto Bundle che contiene i dati salvati dal precedente stato del fragment
        // super si riferisce alla superclasse SignUpFragment

        val loginFragment = LoginFragment()

        binding.LoginFromSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayoutLoginSignIn, loginFragment).commit()
            }
        }
        binding.registrationSuccessTextView.visibility = View.GONE

        binding.signUpButton.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            val matricola: String = binding.MatricolaInput.text.toString()
            val matriculation = "s$matricola@studenti.univpm.it"
            val password: String = binding.PasswordInput.text.toString()
            val confirmPassword: String = binding.ConfirmPasswordInput.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Le password non corrispondono", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Se le password non corrispondono, il codice successivo alla condizione non verrà eseguito
            }

            auth.createUserWithEmailAndPassword(matriculation, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { // Registrazione completata con successo
                        val user = auth.currentUser // Metodo currentUser restituisce un oggetto FirebaseUser che rappresenta l'utente autenticato
                        val userId = user?.uid // Se user non è null, si accede alla proprietà uid

                        val db = FirebaseFirestore.getInstance()
                        // Si accede alla collection Users utilizzando l'ID utente come identificatore del documento.
                        val userDocRef = db.collection("Users").document(userId!!)
                        val role = "Department head"
                        // Oggetto userData di tipo HashMap che contiene le informazioni dell'utente
                        val userData = hashMapOf(
                            "matriculation" to matriculation,
                            "password" to password,
                            "role" to role,
                        )
                        // Salva i dati del nuovo utente contenuti in userData in userDocRef (riferimento al documento specifico dell'utente)
                        userDocRef.set(userData)
                            .addOnSuccessListener { // Salvataggio dei dettagli dell'utente completato con successo
                                binding.registrationSuccessTextView.visibility = View.VISIBLE
                                binding.registrationSuccessTextView.text = "Registrazione avvenuta con successo"
                                // Ritarda la transizione al fragment di login per far apparire a schermo il messaggio di registrazione con successo
                                Handler().postDelayed({
                                    val loginFragment = LoginFragment()
                                    parentFragmentManager.beginTransaction().apply {
                                        replace(R.id.frameLayoutLoginSignIn, loginFragment)
                                        commit()
                                    }
                                }, 200)
                            }
                            .addOnFailureListener { e ->
                                // Errore durante il salvataggio dei dati dell'utente
                                Log.e(TAG, "Errore durante il salvataggio dei dati utente: ${e.message}")
                            }
                    } else {
                        // Errore durante la registrazione dell'utente
                        val errorMessage = task.exception?.message
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }
    }
