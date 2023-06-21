package com.example.polimarche.Managers.Menu.Setup.Create.ChoosingDampers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentManagersChooseSecondSpringBinding
import com.example.polimarche.data_container.spring.SpringViewModel
import com.example.polimarche.users.managers.menu.setup.create.choosing_springs.ChooseSpringsMain

class SecondSpringsFragment(
    private val chooseSpringsMain: ChooseSpringsMain
): Fragment(R.layout.fragment_managers_choose_second_damper){

    private var _binding: FragmentManagersChooseSecondSpringBinding? = null
    private val binding get() = _binding!!

    private val springViewModel: SpringViewModel by viewModels()


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
        val firstDamper = FirstSpringsFragment(chooseSpringsMain)
        binding.previousSpringSecondSpring.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseSprings, firstDamper).commit()
        }

        /*
        The upload will submit the result only when a list of checks will be passed
         */
        binding.uploadSprings.setOnClickListener {
            if (
                springViewModel.getFrontSpringParametersStocked() != null &&
                springViewModel.getBackSpringParametersStocked() != null
            ) {
                // get the values of the parameters stocked
                val frontParameters = springViewModel.getFrontSpringParametersStocked()
                val backParameters = springViewModel.getBackSpringParametersStocked()


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
                    chooseSpringsMain.finish()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Insert spring parameters",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        // sets the parameters to be passed to ExistingBalanceParameters or AddBalanceParameters
        // fragments in order to know whether it's referring to front or back balance.
        val bundle = Bundle()
        bundle.putString("SPRING_POSITION", "End")
        val existingParameters = ExistingSpringsParameters(binding.uploadSprings, binding.previousSpringSecondSpring)
        val addParameters = AddSpringsParameters(binding.uploadSprings, binding.previousSpringSecondSpring)
        existingParameters.arguments = bundle
        addParameters.arguments = bundle

        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
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