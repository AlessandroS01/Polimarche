package com.example.polimarche.users.managers.menu.setup.create.choosing_dampers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentManagersChooseFirstDamperBinding

class FirstDamperFragment(
    private val chooseDampersMain: ChooseDampersMain
): Fragment(R.layout.fragment_managers_choose_first_damper){

    private var _binding: FragmentManagersChooseFirstDamperBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseFirstDamperBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Allow the user to navigate through the different dampers view fragment
        clicking on the image view positioned at the bottom of the page
         */
        val secondDamper = SecondDamperFragment(chooseDampersMain)
        binding.nextDamperFirstDamper.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseDampers, secondDamper).commit()
        }

        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */

        // sets the parameters to be passed to ExistingDamperParameters or AddDamperParameters
        // fragments in order to know whether it's referring to front or back balance.
        val bundle = Bundle()
        bundle.putString("DAMPER_POSITION", "Front")
        val existingParameters = ExistingDampersParameters(binding.nextDamperFirstDamper, null)
        val addParameters = AddDampersParameters(binding.nextDamperFirstDamper, null)
        existingParameters.arguments = bundle
        addParameters.arguments = bundle


        parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseDampers, existingParameters).commit()
        binding.radioGroupFirstDamper.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddDamperParameters.id){
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseDampers, addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(R.id.frameLayoutChoiceChooseDampers, existingParameters).commit()
            }
        }

    }

}