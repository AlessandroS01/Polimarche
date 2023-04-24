package com.example.polimarche.Users.Managers.Menu.Setup.Create.ChoosingWheels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseFirstWheelBinding

class FirstWheelFragment: Fragment(R.layout.fragment_managers_choose_first_wheel){

    private var _binding: FragmentManagersChooseFirstWheelBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseFirstWheelBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val secondWheel = SecondWheelFragment()
        binding.nextWheelFirstWheel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseWheels, secondWheel).commit()
        }


        val existingParameters = ExistingWheelParameters(binding.nextWheelFirstWheel, null)
        val addParameters = AddWheelsParameters(binding.nextWheelFirstWheel, null)
        parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, existingParameters).commit()
        binding.radioGroupFirstWheel.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddParameters.id){
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, existingParameters).commit()
            }
        }


    }

}