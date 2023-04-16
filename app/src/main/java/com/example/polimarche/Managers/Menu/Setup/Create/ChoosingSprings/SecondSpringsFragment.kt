package com.example.polimarche.Managers.Menu.Setup.Create.ChoosingDampers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseSecondSpringBinding

class SecondSpringsFragment: Fragment(R.layout.fragment_managers_choose_second_damper){

    private var _binding: FragmentManagersChooseSecondSpringBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseSecondSpringBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Allow the user to navigate through the different dampers view fragment
        clicking on the image view positioned at the bottom of the page
         */
        val firstDamper = FirstSpringsFragment()
        binding.previousSpringSecondSpring.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseSprings, firstDamper).commit()
        }

        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
        val existingParameters = ExistingSpringsParameters(binding.uploadSprings, binding.previousSpringSecondSpring)
        val addParameters = AddSpringsParameters(binding.uploadSprings, binding.previousSpringSecondSpring)
        parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseSprings, existingParameters).commit()
        binding.radioGroupSecondSpring.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddSpringParameters.id){
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseSprings, addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseSprings, existingParameters).commit()
            }
        }
    }



}