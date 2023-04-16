package com.example.polimarche.Managers.Menu.Setup.Create.ChoosingDampers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseFirstSpringBinding

class FirstSpringsFragment: Fragment(R.layout.fragment_managers_choose_first_spring){

    private var _binding: FragmentManagersChooseFirstSpringBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseFirstSpringBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Allow the user to navigate through the different dampers view fragment
        clicking on the image view positioned at the bottom of the page
         */
        val secondDamper = SecondSpringsFragment()
        binding.nextSpringFirstSpring.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseSprings, secondDamper).commit()
        }

        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
        val existingParameters = ExistingSpringsParameters(binding.nextSpringFirstSpring, null)
        val addParameters = AddSpringsParameters(binding.nextSpringFirstSpring, null)
        parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseSprings, existingParameters).commit()
        binding.radioGroupFirstSpring.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddSpringParameters.id){
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseSprings, addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseSprings, existingParameters).commit()
            }
        }

    }



}