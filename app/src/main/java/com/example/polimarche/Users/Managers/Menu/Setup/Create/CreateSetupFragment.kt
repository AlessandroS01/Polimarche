package com.example.polimarche.Users.Managers.Menu.Setup.Create

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersSetupCreateSetupBinding
import com.example.polimarche.Data.*
import com.example.polimarche.Users.Managers.Menu.Setup.Create.ChoosingBalance.ChooseBalanceMain
import com.example.polimarche.Users.Managers.Menu.Setup.Create.ChoosingDampers.ChooseDampersMain
import com.example.polimarche.Users.Managers.Menu.Setup.Create.ChoosingSprings.ChooseSpringsMain
import com.example.polimarche.Users.Managers.Menu.Setup.Create.ChoosingWheels.ChooseWheelsMain

class CreateSetupFragment : Fragment(R.layout.fragment_managers_setup_create_setup){

    private var _binding: FragmentManagersSetupCreateSetupBinding? = null
    private val binding get() = _binding!!

    /*
    Provide the initialization of the recycler view containing all the different notes
    of a single setup.
    Furthermore the list passed to the recycler view has only one object because
    the behaviour of the list is managed inside the adapter itself.
     */
    private val noteList: DataSetup = newSetupInitializer()
    private lateinit var recyclerViewSetupNotes: RecyclerView
    private lateinit var adapterSetupNotes: SetupNotesAdapter

    private fun newSetupInitializer(): DataSetup{
        return DataSetup(
            1,
            DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
            DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
            DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
            DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
            DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
            DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
            DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
            DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
            DataBalance(5, "Front", 44.0, 56.0),
            DataBalance(6, "Back", 44.0, 56.0),
            "Endurance",
            "1°",
            mutableListOf("")
        )
    }

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
            Intent(it.context, ChooseBalanceMain::class.java).apply{
                startActivity(this)
            }
        }

        /*
        Shows and hides the layout used to choose the front wring hole of the new setup.
         */
        binding.chooseFrontWingHoleCreateSetup.setOnClickListener {
            changeVisibilityViews(binding.layoutFrontWingHole)
        }

        /*
        Shows and hides the layout used to choose the preferred event types
        for which the setup was made for.
         */
        binding.chooseSetupPreferredEvents.setOnClickListener {
            changeVisibilityViews(binding.layoutSetupPreferredEvents)
        }

        /*
        Shows and hides the layout used to choose the setup code of the new setup.
         */
        binding.chooseSetupCodeCreateSetup.setOnClickListener { 
            changeVisibilityViews(binding.layoutSetupCode)
        }

        /*
        Shows and hides the recycler view used to add notes to the
        newest setup.
         */
        binding.addNotesCreateSetup.setOnClickListener {
            changeVisibilityViews(binding.listNotesNewSetup)
        }

        /*
        Setting up the recycler view containing the notes of the newest setup
         */
        recyclerViewSetupNotes = binding.listNotesNewSetup
        adapterSetupNotes = SetupNotesAdapter(noteList)
        val layoutManagerSetupNotes = LinearLayoutManager(this.context)
        recyclerViewSetupNotes.layoutManager = layoutManagerSetupNotes
        recyclerViewSetupNotes.adapter = adapterSetupNotes


    }

    /*
    Changes the visibility of a layout containing an edit Text used
    to set a particular parameter for a new setup.
     */
    private fun changeVisibilityViews(view: View){
        view.isVisible = !view.isVisible
    }

}