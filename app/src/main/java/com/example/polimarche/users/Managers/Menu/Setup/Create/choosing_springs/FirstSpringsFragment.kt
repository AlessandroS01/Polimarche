package com.example.polimarche.Managers.Menu.Setup.Create.ChoosingDampers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersChooseFirstSpringBinding
import com.example.polimarche.users.managers.menu.setup.create.choosing_balance.AddBalanceParameters
import com.example.polimarche.users.managers.menu.setup.create.choosing_balance.ExistingBalanceParameters
import com.example.polimarche.users.managers.menu.setup.create.choosing_springs.ChooseSpringsMain

class FirstSpringsFragment(
    private val chooseSpringsMain: ChooseSpringsMain
): Fragment(R.layout.fragment_managers_choose_first_spring){

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
        val secondDamper = SecondSpringsFragment(chooseSpringsMain)
        binding.nextSpringFirstSpring.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.layoutChooseSprings, secondDamper).commit()
        }


        // sets the parameters to be passed to ExistingBalanceParameters or AddBalanceParameters
        // fragments in order to know whether it's referring to front or back balance.
        val bundle = Bundle()
        bundle.putString("SPRING_POSITION", "Front")
        val existingParameters = ExistingSpringsParameters(binding.nextSpringFirstSpring, null)
        val addParameters = AddSpringsParameters(binding.nextSpringFirstSpring, null)
        existingParameters.arguments = bundle
        addParameters.arguments = bundle


        /*
        Changes the view that provides the user to change the inserting method of the dampers
        parameters.
         */
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