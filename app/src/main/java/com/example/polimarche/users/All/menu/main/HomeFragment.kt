package com.example.polimarche.users.all.menu.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentGeneralMainHomeBinding
import com.example.polimarche.users.managers.menu.practice_session.ManagersPracticeSessionActivity
import com.example.polimarche.users.managers.menu.setup.ManagersSetupActivity
import com.example.polimarche.users.managers.menu.tracks.ManagersTracksActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment(R.layout.fragment_general_main_home){

    private var _binding: FragmentGeneralMainHomeBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralMainHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Recupera l'ID dell'utente autenticato
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        Log.e("ciao", "userID: $currentUser")

        if (currentUser != null) {
            // L'utente è autenticato, puoi accedere alle sue informazioni

            val matricola: String? = currentUser.email
            val matriculation = matricola!!.split("@")[0]
            // Ora puoi utilizzare il valore di "matriculation" come desideri
            binding.textView24.text = matriculation

            // L'utente è autenticato, puoi accedere alle sue informazioni
            val userId: String = currentUser.uid
            Log.e("ciao", "userID: $userId")
            // Ottieni un'istanza di FirebaseFirestore
            val db = FirebaseFirestore.getInstance()

            // Esegui la query per ottenere il valore dell'attributo "role" della tabella "users"
            db.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val role: String? = documentSnapshot.getString("role")
                        Log.e("ciao", "ruolo: $role")
                        // Ora puoi utilizzare il valore di "role" come desideri
                        binding.textView80.text = role
                    }
                }
                .addOnFailureListener { e ->
                    // Gestisci l'eventuale errore nella query
                    Log.e(TAG, "Errore durante la query: ${e.message}")
                }
        }



        /*
        Allows the user to navigate through the menu by clicking
        on the frame layout positioned in the Home.
         */
        binding.frameLayoutSetup.setOnClickListener {
            Intent(context, ManagersSetupActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.frameLayoutPracticeSession.setOnClickListener {
            Intent(context, ManagersPracticeSessionActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.frameLayoutTracks.setOnClickListener {
            Intent(context, ManagersTracksActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

}