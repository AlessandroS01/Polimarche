package com.example.polimarche.users.all.menu.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralMainHomeBinding
import com.example.polimarche.users.managers.menu.practice_session.ManagersPracticeSessionActivity
import com.example.polimarche.users.managers.menu.setup.ManagersSetupActivity
import com.example.polimarche.users.managers.menu.tracks.ManagersTracksActivity

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