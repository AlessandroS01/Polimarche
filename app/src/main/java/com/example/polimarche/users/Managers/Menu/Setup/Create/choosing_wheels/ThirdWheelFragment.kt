package com.example.polimarche.users.managers.menu.setup.create.choosing_wheels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseThirdWheelBinding

class ThirdWheelFragment: Fragment(R.layout.fragment_managers_choose_third_wheel){

    private var _binding: FragmentManagersChooseThirdWheelBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseThirdWheelBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val secondWheel = SecondWheelFragment()
        binding.previousWheelThirdWheel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseWheels, secondWheel).commit()
        }
        val fourthWheel = FourthWheelFragment()
        binding.nextWheelThirdWheel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseWheels, fourthWheel).commit()
        }


        val existingParameters = ExistingWheelParameters(binding.nextWheelThirdWheel, binding.previousWheelThirdWheel)
        val addParameters = AddWheelsParameters(binding.nextWheelThirdWheel, binding.previousWheelThirdWheel)
        parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, existingParameters).commit()
        binding.radioGroupThirdWheel.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddParameters.id){
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, existingParameters).commit()
            }
        }
    }


}