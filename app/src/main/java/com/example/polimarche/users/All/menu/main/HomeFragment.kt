package com.example.polimarche.users.all.menu.main

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
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment(R.layout.fragment_general_main_home){

    private var _binding: FragmentGeneralMainHomeBinding? = null
    private val binding get() = _binding!!

    private var matriculation: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralMainHomeBinding.inflate(inflater, container, false)
        return binding.root
        var matriculation: String = ""

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recupera l'ID dell'utente autenticato
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Ottieni un'istanza del Firestore
        val db = FirebaseFirestore.getInstance()

        val usersCollection = FirebaseFirestore.getInstance().collection("users")
        userId?.let { uid ->
            usersCollection.document(uid).get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val matricola = document.getString("matriculation")
                    // Imposta la matricola nel TextView
                    binding.textView24.text = matriculation
                }
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

        // Imposta il testo della matricola
        binding.textView24.text = matriculation
    }

}