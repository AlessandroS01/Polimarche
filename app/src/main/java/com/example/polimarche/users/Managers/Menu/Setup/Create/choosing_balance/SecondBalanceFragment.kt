package com.example.polimarche.users.managers.menu.setup.create.choosing_balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseSecondBalanceBinding
import com.example.polimarche.data_container.balance.BalanceViewModel

class SecondBalanceFragment(
    private val chooseBalanceMain: ChooseBalanceMain
): Fragment(R.layout.fragment_managers_choose_second_balance){

    private var _binding: FragmentManagersChooseSecondBalanceBinding? = null
    private val binding get() = _binding!!

    private val balanceViewModel: BalanceViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseSecondBalanceBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Allow the user to navigate through the different dampers view fragment
        clicking on the image view positioned at the bottom of the page
         */
        val firstBalance = FirstBalanceFragment(chooseBalanceMain)
        binding.previousBalanceSecondBalance.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseBalance, firstBalance).commit()
        }

        /*
        The upload will submit the result only when
         */
        binding.uploadBalances.setOnClickListener {
            if (balanceViewModel.getFrontBalanceParametersStocked() != null &&
                balanceViewModel.getBackBalanceParametersStocked() != null
            ) {
                // get the values of the parameters stocked
                val frontParameters = balanceViewModel.getFrontBalanceParametersStocked()
                val backParameters = balanceViewModel.getBackBalanceParametersStocked()


                val correctnessCode =
                    frontParameters?.value?.code.toString() != backParameters?.value?.code.toString()
                val correctnessBrake =
                    frontParameters?.value?.brake?.plus(backParameters?.value?.brake!!) == 100.0
                val correctnessWeight =
                    frontParameters?.value?.weight?.plus(backParameters?.value?.weight!!) == 100.0

                // checks tha validity of the parameters
                if (!correctnessCode){
                    Toast.makeText(
                        requireContext(),
                        "Code values cannot be the same",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!correctnessBrake && !correctnessWeight) {
                    Toast.makeText(
                        requireContext(),
                        "Brake and weight parameters are entered incorrectly. The sum should be 100",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!correctnessBrake) {
                    Toast.makeText(
                        requireContext(),
                        "Brake parameters are entered incorrectly. The sum should be 100",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!correctnessWeight) {
                    Toast.makeText(
                        requireContext(),
                        "Weight parameters are entered incorrectly. The sum should be 100",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // if it the parameters pass all the validation point then it closes the fragment
                    chooseBalanceMain.finish()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Insert balance parameters",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // sets the parameters to be passed to ExistingBalanceParameters or AddBalanceParameters
        // fragments in order to know whether it's referring to front or back balance.
        val bundle = Bundle()
        bundle.putString("BALANCE_POSITION", "Back")
        val existingParameters = ExistingBalanceParameters(binding.uploadBalances, binding.previousBalanceSecondBalance)
        val addParameters = AddBalanceParameters(binding.uploadBalances, binding.previousBalanceSecondBalance)
        existingParameters.arguments = bundle
        addParameters.arguments = bundle
        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
        parentFragmentManager.beginTransaction().replace(binding.frameLayoutChoiceChooseBalance.id, existingParameters).commit()
        binding.radioGroupSecondBalance.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddBalanceParameters.id){
                parentFragmentManager.beginTransaction().replace(binding.frameLayoutChoiceChooseBalance.id
                    , addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(binding.frameLayoutChoiceChooseBalance.id
                    , existingParameters).commit()
            }
        }
    }

}