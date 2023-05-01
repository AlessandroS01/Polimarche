package com.example.polimarche.Users.All.Menu.Main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralMainHomeBinding
import com.example.polimarche.Users.Managers.Menu.PracticeSession.ManagersPracticeSession
import com.example.polimarche.Users.Managers.Menu.Setup.ManagersSetup
import com.example.polimarche.Users.Managers.Menu.Tracks.ManagersTracks

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
            Intent(context, ManagersSetup::class.java).apply {
                startActivity(this)
            }
        }
        binding.frameLayoutPracticeSession.setOnClickListener {
            Intent(context, ManagersPracticeSession::class.java).apply {
                startActivity(this)
            }
        }
        binding.frameLayoutTracks.setOnClickListener {
            Intent(context, ManagersTracks::class.java).apply {
                startActivity(this)
            }
        }
    }

}