package com.example.polimarche.Managers.Menu.Setup.Create.ChoosingWheels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseFourthWheelBinding

class FourthWheelFragment: Fragment(R.layout.fragment_managers_choose_fourth_wheel){

    private var _binding: FragmentManagersChooseFourthWheelBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseFourthWheelBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val thirdFragment = ThirdWheelFragment()
        binding.previousWheelFourthWheel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseWheels, thirdFragment).commit()
        }


        val existingParameters = ExistingParameters(binding.previousWheelFourthWheel, binding.uploadWheels)
        val addParameters = AddParameters(binding.previousWheelFourthWheel, binding.uploadWheels)
        parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, existingParameters).commit()
        binding.radioGroupFourthWheel.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddParameters.id){
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseWheels, addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayoutChoiceChooseWheels, existingParameters).commit()
            }
        }
    }

}