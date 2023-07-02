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

class HomeFragment : Fragment(R.layout.fragment_general_main_home) {

    private var _binding: FragmentGeneralMainHomeBinding? = null
    private val binding get() = _binding!!

    private var role: String? = null

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

        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val matricola: String? = currentUser.email
            val matriculation = matricola!!.split("@")[0]?.substring(1, matricola.indexOf("@"))
            binding.textView24.text = matriculation

            val userId: String = currentUser.uid
            val db = FirebaseFirestore.getInstance()

            db.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        role = documentSnapshot.getString("role")
                        binding.textView80.text = role

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
                    Log.e(TAG, "Errore durante la query: ${e.message}")
                }
        }
    }
}
