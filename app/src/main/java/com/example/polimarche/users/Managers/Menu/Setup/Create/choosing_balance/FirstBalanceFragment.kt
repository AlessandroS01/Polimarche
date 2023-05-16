package com.example.polimarche.users.managers.menu.setup.create.choosing_balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseFirstBalanceBinding
import com.example.polimarche.data_container.balance.BalanceViewModel
import com.example.polimarche.users.managers.menu.setup.delete.VisualizeSetupFragment

class FirstBalanceFragment: Fragment(R.layout.fragment_managers_choose_first_balance){

    private var _binding: FragmentManagersChooseFirstBalanceBinding? = null
    private val binding get() = _binding!!

    private val balanceViewModel: BalanceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersChooseFirstBalanceBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Allow the user to navigate through the different dampers view fragment
        clicking on the image view positioned at the bottom of the page
         */
        val secondBalance = SecondBalanceFragment()
        binding.nextBalanceFirstBalance.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseBalance, secondBalance).commit()
        }

        // sets the parameters to be passed to ExistingBalanceParameters or AddBalanceParameters
        // fragments in order to know whether it's referring to front or back balance.
        val bundle = Bundle()
        bundle.putString("BALANCE_POSITION", "Front")
        val existingParameters = ExistingBalanceParameters(binding.nextBalanceFirstBalance, null)
        val addParameters = AddBalanceParameters(binding.nextBalanceFirstBalance, null)
        existingParameters.arguments = bundle
        addParameters.arguments = bundle
        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
        parentFragmentManager.beginTransaction().replace(binding.frameLayoutChoiceChooseBalance.id
            , existingParameters).commit()
        binding.radioGroupFirstBalance.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonAddBalanceParameters.id){
                parentFragmentManager.beginTransaction().replace(binding.frameLayoutChoiceChooseBalance.id,
                    addParameters).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(binding.frameLayoutChoiceChooseBalance.id
                    , existingParameters).commit()
            }
        }

    }

}