package com.example.polimarche.users.managers.menu.setup.create

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersSetupCreateSetupBinding
import com.example.polimarche.data_container.balance.BalanceViewModel
import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.damper.DataDamper
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.wheel.DataWheel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Opens the layout used to choose the wheels of the new setup.
         */
        binding.chooseWheelsCreateSetup.setOnClickListener {
            Intent(it.context, ChooseWheelsMain::class.java).apply{
                startActivity(this)
            }
        }

        /*
        Opens the layout used to choose the dampers of the new setup.
         */
        binding.chooseDampersCreateSetup.setOnClickListener {
            Intent(it.context, ChooseDampersMain::class.java).apply{
                startActivity(this)
            }
        }

        /*
        Opens the layout used to choose the springs of the new setup.
         */
        binding.chooseSpringsCreateSetup.setOnClickListener {
            Intent(it.context, ChooseSpringsMain::class.java).apply{
                startActivity(this)
            }
        }

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
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerViewBalanceParameters.layoutManager = linearLayoutManager
        recyclerViewBalanceParameters.adapter = adapterBalanceParameters

        /*
        Setting up the recycler view containing the notes of the newest setup
         */
        recyclerViewSetupNotes = binding.listNotesNewSetup
        adapterSetupNotes = SetupNotesAdapter(setupViewModel)
        val layoutManagerSetupNotes = LinearLayoutManager(this.context)
        recyclerViewSetupNotes.layoutManager = layoutManagerSetupNotes
        recyclerViewSetupNotes.adapter = adapterSetupNotes

    }

    override fun onResume() {
        // everytime the fragment resume it should refresh the adapters of all the different
        // recyclerViews
        super.onResume()

        adapterBalanceParameters.setNewList(
            balanceViewModel.getStockedParameters()
        )
    }
}