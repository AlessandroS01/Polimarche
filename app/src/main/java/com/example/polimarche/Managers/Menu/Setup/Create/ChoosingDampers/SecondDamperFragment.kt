package com.example.polimarche.Managers.Menu.Setup.Create.ChoosingDampers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseSecondDamperBinding

class SecondDamperFragment: Fragment(R.layout.fragment_managers_choose_second_damper){

    private var _binding: FragmentManagersChooseSecondDamperBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseSecondDamperBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Allow the user to navigate through the different dampers view fragment
        clicking on the image view positioned at the bottom of the page
         */
        val firstDamper = FirstDamperFragment()
        binding.previousDamperSecondDamper.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseDampers, firstDamper).commit()
        }

        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
        val existingParameters = ExistingDampersParameters(binding.uploadDampers, binding.previousDamperSecondDamper)
        val addParameters = AddDampersParameters(binding.uploadDampers, binding.previousDamperSecondDamper)
        parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseDampers, existingParameters).commit()
        binding.radioGroupSecondDamper.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddDamperParameters.id){
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseDampers, addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseDampers, existingParameters).commit()
            }
        }
    }

}