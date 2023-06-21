package com.example.polimarche.users.managers.menu.setup.create

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentManagersSetupCreateSetupBinding
import com.example.polimarche.data_container.balance.BalanceViewModel
import com.example.polimarche.data_container.damper.DamperViewModel
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel
import com.example.polimarche.data_container.spring.SpringViewModel
import com.example.polimarche.data_container.wheel.WheelViewModel
import com.example.polimarche.users.managers.menu.setup.create.choosing_balance.BalanceAdapter
import com.example.polimarche.users.managers.menu.setup.create.choosing_balance.ChooseBalanceMain
import com.example.polimarche.users.managers.menu.setup.create.choosing_dampers.ChooseDampersMain
import com.example.polimarche.users.managers.menu.setup.create.choosing_dampers.DampersAdapter
import com.example.polimarche.users.managers.menu.setup.create.choosing_springs.ChooseSpringsMain
import com.example.polimarche.users.managers.menu.setup.create.choosing_springs.SpringsAdapter
import com.example.polimarche.users.managers.menu.setup.create.choosing_wheels.ChooseWheelsMain
import com.example.polimarche.users.managers.menu.setup.create.choosing_wheels.WheelsAdapter

class CreateSetupFragment : Fragment(R.layout.fragment_managers_setup_create_setup){

    private var _binding: FragmentManagersSetupCreateSetupBinding? = null
    private val binding get() = _binding!!

    private val setupViewModel: SetupViewModel by viewModels()
    private val wheelViewModel: WheelViewModel by viewModels()
    private val damperViewModel: DamperViewModel by viewModels()
    private val springViewModel: SpringViewModel by viewModels()
    private val balanceViewModel: BalanceViewModel by viewModels()


    private lateinit var adapterWheelParameters: WheelsAdapter

    private lateinit var adapterDamperParameters: DampersAdapter

    private lateinit var adapterSpringParameters: SpringsAdapter

    private lateinit var adapterBalanceParameters: BalanceAdapter



    /*
    Provide the initialization of the recycler view containing all the different notes
    of a single setup.
    Furthermore the list passed to the recycler view has only one object because
    the behaviour of the list is managed inside the adapter itself.
     */
    private lateinit var recyclerViewSetupNotes: RecyclerView
    private lateinit var adapterSetupNotes: SetupNotesAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagersSetupCreateSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        // everytime the fragment resume it should refresh the adapters of all the different
        // recyclerViews
        super.onResume()

        adapterBalanceParameters.setNewList(
            balanceViewModel.getStockedParameters()
        )

        adapterWheelParameters.setNewList(
            wheelViewModel.getStockedParameters()
        )

        adapterDamperParameters.setNewList(
            damperViewModel.getStockedParameters()
        )

        adapterSpringParameters.setNewList(
            springViewModel.getStockedParameters()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Opens the layout used to choose the wheels of the new setup.
         */
        binding.chooseWheelsCreateSetup.setOnClickListener {
            // when the user wants to change the parameters it would be required
            // to add the parameters again
            wheelViewModel.clearStockedParameters()
            adapterWheelParameters.setNewList(wheelViewModel.getStockedParameters())
            Intent(it.context, ChooseWheelsMain::class.java).apply{
                startActivity(this)
            }
        }
        val recyclerViewWheelParameters = binding.listWheelParameters
        adapterWheelParameters = WheelsAdapter("Stocked", wheelViewModel)
        val linearLayoutManagerWheelParameters = LinearLayoutManager(requireContext())
        recyclerViewWheelParameters.layoutManager = linearLayoutManagerWheelParameters
        recyclerViewWheelParameters.adapter = adapterWheelParameters


        /*
        Opens the layout used to choose the dampers of the new setup.
         */
        binding.chooseDampersCreateSetup.setOnClickListener {
            // when the user wants to change the parameters it would be required
            // to add the parameters again
            damperViewModel.clearStockedParameters()
            adapterDamperParameters.setNewList(damperViewModel.getStockedParameters())
            Intent(it.context, ChooseDampersMain::class.java).apply{
                startActivity(this)
            }
        }
        val recyclerViewDamperParameters = binding.listDamperParameters
        adapterDamperParameters = DampersAdapter("Stocked", damperViewModel)
        val linearLayoutManagerDamperParameters = LinearLayoutManager(requireContext())
        recyclerViewDamperParameters.layoutManager = linearLayoutManagerDamperParameters
        recyclerViewDamperParameters.adapter = adapterDamperParameters


        /*
        Opens the layout used to choose the springs of the new setup.
         */
        binding.chooseSpringsCreateSetup.setOnClickListener {
            // when the user wants to change the parameters it would be required
            // to add the parameters again
            springViewModel.clearStockedParameters()
            adapterSpringParameters.setNewList(springViewModel.getStockedParameters())
            Intent(it.context, ChooseSpringsMain::class.java).apply{
                startActivity(this)
            }
        }
        val recyclerViewSpringParameters = binding.listSpringParameters
        adapterSpringParameters = SpringsAdapter("Stocked", springViewModel)
        val linearLayoutManagerSpringParameters = LinearLayoutManager(requireContext())
        recyclerViewSpringParameters.layoutManager = linearLayoutManagerSpringParameters
        recyclerViewSpringParameters.adapter = adapterSpringParameters


        /*
        Opens the layout used to choose the balance of the new setup.
         */
        binding.chooseBalanceCreateSetup.setOnClickListener {
            // when the user wants to change the parameters it would be required
            // to add the parameters again
            balanceViewModel.clearStockedParameters()
            adapterBalanceParameters.setNewList(balanceViewModel.getStockedParameters())
            Intent(it.context, ChooseBalanceMain::class.java).apply{
                startActivity(this)
            }
        }
        val recyclerViewBalanceParameters = binding.listBalanceParameters
        adapterBalanceParameters = BalanceAdapter("Stocked", balanceViewModel)
        val linearLayoutManagerBalanceParameters = LinearLayoutManager(requireContext())
        recyclerViewBalanceParameters.layoutManager = linearLayoutManagerBalanceParameters
        recyclerViewBalanceParameters.adapter = adapterBalanceParameters

        /*
        Setting up the recycler view containing the notes of the newest setup
         */
        recyclerViewSetupNotes = binding.listNotesNewSetup
        adapterSetupNotes = SetupNotesAdapter()
        val layoutManagerSetupNotes = LinearLayoutManager(this.context)
        recyclerViewSetupNotes.layoutManager = layoutManagerSetupNotes
        recyclerViewSetupNotes.adapter = adapterSetupNotes

        // starts the process to create a new setup
        binding.createSetup.setOnClickListener {
            // checks if all the parameters are given
            if(
                adapterWheelParameters.itemCount != 0
                &&
                adapterDamperParameters.itemCount != 0
                &&
                adapterSpringParameters.itemCount != 0
                &&
                adapterBalanceParameters.itemCount != 0
            ) {
                // checks if the front wing hole is set
               if(
                   binding.editTextFrontWing.text.isNotEmpty()
               ){
                   // whenever it passes all the verification it can start the cretion of the newest
                   // setup
                   createSetup()

               }else{
                   Toast.makeText(
                       requireContext(),
                       "Provide front wing hole",
                       Toast.LENGTH_SHORT
                   ).show()
               }

            }else{
                Toast.makeText(
                    requireContext(),
                    "Provide wheel, damper, spring and balance parameters.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createSetup(){
        if(binding.editTextSetupCode.text.isNotEmpty()){
            val newSetupCode = binding.editTextSetupCode.text.toString().toInt()

            if ( setupViewModel.setupList.none{
                    it.code == newSetupCode
                }
            ){
                generateSetup(newSetupCode)
            }
        }else{
            var newSetupCode = 1
            while (
                setupViewModel.setupList.any{
                    it.code == newSetupCode
                }
            ){
                newSetupCode++
            }
            generateSetup(newSetupCode)
        }
    }


    private fun generateSetup(newSetupCode: Int){
        var preferredEvents = ""

        if ( binding.checkBoxAcceleration.isChecked )
            preferredEvents += binding.checkBoxAcceleration.text.toString() + "\n"
        if ( binding.checkBoxAutocross.isChecked )
            preferredEvents += binding.checkBoxAutocross.text.toString() + "\n"
        if ( binding.checkBoxEndurance.isChecked )
            preferredEvents += binding.checkBoxEndurance.text.toString() + "\n"
        if ( binding.checkBoxSkidpad.isChecked )
            preferredEvents += binding.checkBoxSkidpad.text.toString()

        val setupNotes = adapterSetupNotes.retrieveNoteList()

        val newSetup = DataSetup(
            newSetupCode,

            wheelViewModel.getFrontRightParametersStocked()?.value!!,
            wheelViewModel.getFrontLeftParametersStocked()?.value!!,
            wheelViewModel.getRearRightParametersStocked()?.value!!,
            wheelViewModel.getRearLeftParametersStocked()?.value!!,

            damperViewModel.getFrontDamperParametersStocked()?.value!!,
            damperViewModel.getBackDamperParametersStocked()?.value!!,

            springViewModel.getFrontSpringParametersStocked()?.value!!,
            springViewModel.getBackSpringParametersStocked()?.value!!,

            balanceViewModel.getFrontBalanceParametersStocked()?.value!!,
            balanceViewModel.getBackBalanceParametersStocked()?.value!!,

            preferredEvents,

            binding.editTextFrontWing.text.toString(),

            setupNotes
        )

        // adds the new setup created to the repository
        setupViewModel.addNewSetup(newSetup)

        // updates the various list of parameters
        wheelViewModel.addNewWheelParameters()
        damperViewModel.addNewDamperParameters()
        springViewModel.addNewSpringParameters()
        balanceViewModel.addNewBalanceParameters()

        Toast.makeText(
            requireContext(),
            "Setup created. All input cleared.",
            Toast.LENGTH_SHORT
        ).show()

        // after the creation of the setup it should clear all the inputs inside the view
        clearInput()
    }

    private fun clearInput(){
        // clear all the inputs inside the view
        wheelViewModel.clearStockedParameters()
        adapterWheelParameters.setNewList(wheelViewModel.getStockedParameters())
        damperViewModel.clearStockedParameters()
        adapterDamperParameters.setNewList(damperViewModel.getStockedParameters())
        springViewModel.clearStockedParameters()
        adapterSpringParameters.setNewList(springViewModel.getStockedParameters())
        balanceViewModel.clearStockedParameters()
        adapterBalanceParameters.setNewList(balanceViewModel.getStockedParameters())

        binding.editTextFrontWing.text.clear()

        if ( binding.checkBoxAcceleration.isChecked )
            binding.checkBoxAcceleration.isChecked = false
        if ( binding.checkBoxAutocross.isChecked )
            binding.checkBoxAutocross.isChecked = false
        if ( binding.checkBoxEndurance.isChecked )
            binding.checkBoxEndurance.isChecked = false
        if ( binding.checkBoxSkidpad.isChecked )
            binding.checkBoxSkidpad.isChecked = false

        binding.editTextSetupCode.text.clear()

        adapterSetupNotes.clearNoteList()
    }
}