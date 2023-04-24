package com.example.polimarche.Users.Managers.Menu.Setup.Create.ChoosingBalance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseSecondBalanceBinding

class SecondBalanceFragment: Fragment(R.layout.fragment_managers_choose_second_balance){

    private var _binding: FragmentManagersChooseSecondBalanceBinding? = null
    private val binding get() = _binding!!


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
        val firstBalance = FirstBalanceFragment()
        binding.previousBalanceSecondBalance.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseBalance, firstBalance).commit()
        }

        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
        val existingParameters = ExistingBalanceParameters(binding.uploadBalances, binding.previousBalanceSecondBalance)
        val addParameters = AddBalanceParameters(binding.uploadBalances, binding.previousBalanceSecondBalance)
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