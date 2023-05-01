package com.example.polimarche.Users.All.Menu.Setup.Problem

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralSetupOccurringProblemBinding
import com.example.polimarche.Data.*
import com.example.polimarche.Users.All.Adapters.AddOccurringProblemAdapter
import com.example.polimarche.Users.All.Adapters.OccurringProblemAdapter

class OccurringProblemFragment: Fragment(R.layout.fragment_general_setup_occurring_problem) {

    private var _binding: FragmentGeneralSetupOccurringProblemBinding? = null
    private val binding get()= _binding!!

    private var listOccurringProblem: MutableList<DataOccurringProblem> = initializeOccurringProblem()
    private lateinit var adapterMain: OccurringProblemAdapter
    private lateinit var recyclerViewMain: RecyclerView

    private val listSetupAddOccurringProblem: MutableList<DataSetup> = initializeSetup()
    private lateinit var adapterAddOccurringProblems: AddOccurringProblemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralSetupOccurringProblemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterMain = OccurringProblemAdapter(listOccurringProblem)
        recyclerViewMain = binding.listOccurringProblemSetup
        val linearLayoutManagerMain = LinearLayoutManager(this.context)
        recyclerViewMain.layoutManager = linearLayoutManagerMain
        recyclerViewMain.adapter = adapterMain


        binding.imageButtonAddOccurringProblem.setOnClickListener {
            showDialog()
        }
    }

    /*
    Create a dialog box that let the user add a new setup in which the problem
    previously selected occurs.
     */
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_add_occurring_problem)

        val confirmAddition: FrameLayout = dialog.findViewById(R.id.confirmAddNewOccurringProblem)
        val closeAddition: FrameLayout = dialog.findViewById(R.id.closeAddNewOccurringProblem)

        val recyclerViewAddOccurringProblem: RecyclerView = dialog.findViewById(R.id.listAddNewOccurringProblem)
        adapterAddOccurringProblems = AddOccurringProblemAdapter(listSetupAddOccurringProblem)
        val linearLayoutManagerAddProblem = LinearLayoutManager(this.context)
        recyclerViewAddOccurringProblem.layoutManager = linearLayoutManagerAddProblem
        recyclerViewAddOccurringProblem.adapter = adapterAddOccurringProblems

        dialog.show()
        /*
        The confirm button check weather if a checkbox inside the recycler view
        isChecked or not using the list created inside the adapter itself.
        If it's checked then it reads first the code of the setup inside listSetupAddOccurringProblem
        in the same position of the checkbox checked and, then, it reads the value of the description
        given using, as for the checkBox, the list created inside the adapter that keeps updated
        all the descriptions for every setup shown.
         */
        confirmAddition.setOnClickListener {
            adapterAddOccurringProblems.getListCheckedElements().forEachIndexed { index, b ->
                    if(b){
                        val newOccurringProblem = DataOccurringProblem(1,
                            listSetupAddOccurringProblem[index].code,
                            adapterAddOccurringProblems.getListDescriptionElements()[index]
                            )

                        listOccurringProblem.add(newOccurringProblem)
                    }
            }
            adapterMain.notifyDataSetChanged()
            Toast.makeText(this.context, "New setups facing the problem added", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        closeAddition.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun initializeOccurringProblem(): MutableList<DataOccurringProblem>{
        return mutableListOf(
            DataOccurringProblem(1, 1, "Rottura posteriore"),
            DataOccurringProblem(1, 4, "Rottura posteriore"),
            DataOccurringProblem(1, 5, "Rottura posteriore"),
            DataOccurringProblem(1, 6, "Rottura posteriore"),
            DataOccurringProblem(1, 7, "Rottura posteriore"),
            DataOccurringProblem(1, 8, "Rottura posteriore"),
            DataOccurringProblem(2, 8, "Rottura posteriore"),
            DataOccurringProblem(3, 9, "Rottura posteriore"),
        ).filter { it.problemCode == 1 }.toMutableList()
    }

    private fun initializeSetup(): MutableList<DataSetup>{
        return mutableListOf(
            DataSetup(
                2,
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
            ),
            DataSetup(
                3,
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
        )
    }
}