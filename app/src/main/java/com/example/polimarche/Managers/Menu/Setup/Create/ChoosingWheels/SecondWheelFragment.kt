package com.example.polimarche.Managers.Menu.Setup.Create.ChoosingWheels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseSecondWheelBinding

class SecondWheelFragment: Fragment(R.layout.fragment_managers_choose_second_wheel){

    private var _binding: FragmentManagersChooseSecondWheelBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseSecondWheelBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val firstFragment = FirstWheelFragment()
        binding.previousWheelSecondWheel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseWheels, firstFragment).commit()
        }
        val thirdFragment = ThirdWheelFragment()
        binding.nextWheelSecondWheel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseWheels, thirdFragment).commit()
        }


        val existingParameters = ExistingWheelParameters(binding.nextWheelSecondWheel, binding.previousWheelSecondWheel)
        val addParameters = AddWheelsParameters(binding.nextWheelSecondWheel, binding.previousWheelSecondWheel)
        parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, existingParameters).commit()
        binding.radioGroupSecondWheel.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddParameters.id){
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, existingParameters).commit()
            }
        }
    }


}