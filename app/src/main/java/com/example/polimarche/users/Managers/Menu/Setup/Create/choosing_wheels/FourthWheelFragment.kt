package com.example.polimarche.users.managers.menu.setup.create.choosing_wheels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentManagersChooseFourthWheelBinding
import com.example.polimarche.data_container.wheel.WheelViewModel

class FourthWheelFragment(
    private val chooseWheelActivity: ChooseWheelsMain
): Fragment(R.layout.fragment_managers_choose_fourth_wheel){

    private var _binding: FragmentManagersChooseFourthWheelBinding? = null
    private val binding get() = _binding!!

    private val wheelViewModel: WheelViewModel by viewModels()


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

        val thirdWheel = ThirdWheelFragment(chooseWheelActivity)
        binding.previousWheelFourthWheel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseWheels, thirdWheel).commit()
        }

        binding.uploadWheels.setOnClickListener {
            if (wheelViewModel.getFrontRightParametersStocked() != null
                &&
                wheelViewModel.getFrontLeftParametersStocked() != null
                &&
                wheelViewModel.getRearRightParametersStocked() != null
                &&
                wheelViewModel.getRearLeftParametersStocked() != null
            ) {

                // return true if all the code used are different
                val correctnessCode =
                    findCorrectnessCode()

                // checks tha validity of the parameters
                if (!correctnessCode) {
                    Toast.makeText(
                        requireContext(),
                        "Code values cannot be the same",
                        Toast.LENGTH_SHORT
                    ).show()
                } else{
                    chooseWheelActivity.finish()
                }
            }
            else {
                    Toast.makeText(
                        requireContext(),
                        "Insert wheel parameters",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

        val existingParameters = ExistingWheelParameters(binding.previousWheelFourthWheel, binding.uploadWheels)
        val addParameters = AddWheelsParameters(binding.previousWheelFourthWheel, binding.uploadWheels)

        val bundle = Bundle()
        bundle.putString("WHEEL_POSITION", "Rear left")
        existingParameters.arguments = bundle
        addParameters.arguments = bundle

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

    // Checks if every code used for the wheels is different from the others
    private fun findCorrectnessCode(): Boolean {
        val listWheelParametersCode = emptyList<Int>().toMutableList()

        // add all the codes to a list
        wheelViewModel.getStockedParameters().forEach {
                listWheelParametersCode.add(
                    it.code
                )
        }

        // check if the list size is equal to the set size created from the list
        // a set is a collection of elements in which all the elements are different
        return(listWheelParametersCode.size == listWheelParametersCode.toSet().size)
    }

}