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
import com.example.polimarche.users.department_head.menu.practice_session.DepartmentHeadsPracticeSessionActivity
import com.example.polimarche.users.department_head.menu.setup.DepartmentHeadsSetupActivity
import com.example.polimarche.users.department_head.menu.tracks.DepartmentHeadsTracksActivity
import com.example.polimarche.users.managers.menu.practice_session.ManagersPracticeSessionActivity
import com.example.polimarche.users.managers.menu.setup.ManagersSetupActivity
import com.example.polimarche.users.managers.menu.tracks.ManagersTracksActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_general_main_home) {

    // Variabili utilizzate per eseguire il binding degli elementi del layout
    // fragment_general_main_home utilizzando FragmentGeneralMainHomeBinding
    private var _binding: FragmentGeneralMainHomeBinding? = null
    private val binding get() = _binding!!

    private var role: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneralMainHomeBinding.inflate(inflater, container, false)
        // La funzione inflate prende il layout XML del fragment_general_main_home
        // e lo converte in un oggetto FragmentGeneralMainHomeBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viene ottenuto l'utente corrente tramite il FirebaseAuth e assegnato a currentUser
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        // Controllo che currentUser non sia null
        if (currentUser != null) {

            val matricola: String? = currentUser.email // Viene ottenuta la matricola dell'utente
            // estrae il valore della matricola dall'email dell'utente
            val matriculation = matricola!!.split("@")[0].substring(1, matricola.indexOf("@"))
            binding.textView24.text = matriculation // imposta matriculation come testo di textView24

            val userId: String = currentUser.uid // Viene ottenuto l'ID dell'utente corrente
            val db = FirebaseFirestore.getInstance() // Viene ottenuta un'istanza di FirebaseFirestore

            // Viene avviata una coroutine nell'ambito del thread di I/O
            CoroutineScope(Dispatchers.IO).launch {
                // Viene effettuata una query al documento nel database corrispondente all'utente corrente
                db.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) { // se il documento relativo all'utente esiste
                        role = documentSnapshot.getString("role") // Viene ottenuto il valore del campo "role"
                        binding.textView80.text = role // e lo si setta come testo di textView80

                        // Vengono impostati listener per il click su vari elementi dell'interfaccia utente
                        binding.frameLayoutSetup.setOnClickListener {
                            if (role == "Manager") {
                                Intent(context, ManagersSetupActivity::class.java).apply {
                                    startActivity(this)
                                }
                            } else if (role == "Department head") {
                                Intent(context, DepartmentHeadsSetupActivity::class.java).apply {
                                    startActivity(this)
                                }
                            }
                        }

                        // Vengono impostati listener per il click su vari elementi dell'interfaccia utente
                        binding.frameLayoutPracticeSession.setOnClickListener {
                            if (role == "Manager") {
                                Intent(context, ManagersPracticeSessionActivity::class.java).apply {
                                    startActivity(this)
                                }
                            } else if (role == "Department head") {
                                Intent(context, DepartmentHeadsPracticeSessionActivity::class.java).apply {
                                    startActivity(this)
                                }
                            }
                        }

                        // Vengono impostati listener per il click su vari elementi dell'interfaccia utente
                        binding.frameLayoutTracks.setOnClickListener {
                            if (role == "Manager") {
                                Intent(context, ManagersTracksActivity::class.java).apply {
                                    startActivity(this)
                                }
                            } else if (role == "Department head") {
                                Intent(context, DepartmentHeadsTracksActivity::class.java).apply {
                                    startActivity(this)
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    // Se la query non va a buon fine viene stampato nel logcat un messaggio di errore
                    Log.e(TAG, "Errore durante la query: ${e.message}")
                }
            }

        }
    }
}
