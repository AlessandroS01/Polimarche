package com.example.polimarche.users.managers.menu.setup.create.choosing_dampers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseSecondDamperBinding
import com.example.polimarche.data_container.damper.DamperViewModel
import com.example.polimarche.users.managers.menu.setup.create.choosing_balance.ChooseBalanceMain

class SecondDamperFragment(
    private val chooseDampersMain: ChooseDampersMain
): Fragment(R.layout.fragment_managers_choose_second_damper){

    private var _binding: FragmentManagersChooseSecondDamperBinding? = null
    private val binding get() = _binding!!

    private val damperViewModel: DamperViewModel by viewModels()


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
        val firstDamper = FirstDamperFragment(chooseDampersMain)
        binding.previousDamperSecondDamper.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseDampers, firstDamper).commit()
        }

        /*
        The upload will submit the result only when
         */
        binding.uploadDampers.setOnClickListener {
            if (damperViewModel.getFrontDamperParametersStocked() != null &&
                damperViewModel.getBackDamperParametersStocked() != null
            ) {
                // get the values of the parameters stocked
                val frontParameters = damperViewModel.getFrontDamperParametersStocked()
                val backParameters = damperViewModel.getBackDamperParametersStocked()


                val correctnessCode =
                    frontParameters?.value?.code.toString() != backParameters?.value?.code.toString()

                // checks tha validity of the parameters
                if (!correctnessCode){
                    Toast.makeText(
                        requireContext(),
                        "Code values cannot be the same",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // if it the parameters pass all the validation checks then it closes the fragment
                    chooseDampersMain.finish()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Insert damper parameters",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



        // sets the parameters to be passed to ExistingDamperParameters or AddDamperParameters
        // fragments in order to know whether it's referring to front or back balance.
        val bundle = Bundle()
        bundle.putString("DAMPER_POSITION", "End")
        val existingParameters = ExistingDampersParameters(binding.uploadDampers, binding.previousDamperSecondDamper)
        val addParameters = AddDampersParameters(binding.uploadDampers, binding.previousDamperSecondDamper)
        existingParameters.arguments = bundle
        addParameters.arguments = bundle

        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
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