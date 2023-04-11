package com.example.polimarche.Managers.Menu.Setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersSetupCreateSetupBinding

class MenuCreateSetupFragment : Fragment(R.layout.fragment_managers_setup_create_setup){

    private var _binding: FragmentManagersSetupCreateSetupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagersSetupCreateSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.chooseWheelsCreateSetup.setOnClickListener {
            Toast.makeText(this.context, "Wheels", Toast.LENGTH_SHORT).show()
        }
    }
}