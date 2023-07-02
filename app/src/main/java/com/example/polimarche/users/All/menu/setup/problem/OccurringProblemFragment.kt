package com.example.polimarche.users.all.menu.setup.problem

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentGeneralSetupOccurringProblemBinding
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.occurring_problem.DataOccurringProblem
import com.example.polimarche.data_container.problem.occurring_problem.OccurringProblemViewModel
import com.example.polimarche.data_container.problem.solved_problem.SolvedProblemViewModel
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel

class OccurringProblemFragment(
    private val problemClicked: DataProblem
): Fragment(R.layout.fragment_general_setup_occurring_problem),
    OccurringProblemAdapter.OnProblemSolvedClick
{

    private val setupViewModel: SetupViewModel by viewModels()
    private val occurringProblemViewModel: OccurringProblemViewModel by viewModels()
    private val solvedProblemViewModel: SolvedProblemViewModel by viewModels()


    private var _binding: FragmentGeneralSetupOccurringProblemBinding? = null
    private val binding get()= _binding!!


    private lateinit var occurringProblemAdapter: OccurringProblemAdapter
    private lateinit var recyclerViewOccurringProblem: RecyclerView

    /*
    List of setups in which the problem clicked doesn't occur and it's not been solved.
     */
    private lateinit var adapterAddOccurringProblems: AddNewOccurringProblemAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralSetupOccurringProblemBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        Passes to the adapter the list of problems that matches with the problem code clicked
        on ProblemsSetupFragment.
         */

        occurringProblemViewModel.listOccurringProblem.observe(viewLifecycleOwner) {
            occurringProblemAdapter = OccurringProblemAdapter(
                problemClicked,
                occurringProblemViewModel,
                this
            )

            recyclerViewOccurringProblem = binding.listOccurringProblemSetup
            val linearLayoutManagerMain = LinearLayoutManager(this.context)
            recyclerViewOccurringProblem.layoutManager = linearLayoutManagerMain
            recyclerViewOccurringProblem.adapter = occurringProblemAdapter
        }

        binding.imageButtonAddOccurringProblem.setOnClickListener {
            showDialogAddOccurringProblem()
        }
    }

    /*
    Create a dialog box that let the user add a new setup in which the problem
    previously selected occurs.
     */
    private fun showDialogAddOccurringProblem() {
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

        val textView: TextView = dialog.findViewById(R.id.textViewChanges)

        val confirmAddition: FrameLayout = dialog.findViewById(R.id.confirmAddNewOccurringProblem)
        val closeAddition: FrameLayout = dialog.findViewById(R.id.closeAddNewOccurringProblem)

        val recyclerViewAddOccurringProblem: RecyclerView = dialog.findViewById(
            R.id.listAddNewOccurringProblem
        )
        /*
        Create a list of setups in which all elements have a setupCode different from
        the other setup codes stored inside listOccurringProblem.
         */
        val listSetupsWithoutProblem: MutableList<DataSetup> = findSetupsWithoutProblem()
        /*
        Check if there are other problems in which the problem can occur.
        If there aren't then a toast will be shown.
         */
        if (listSetupsWithoutProblem.size == 0){
            textView.text = "There isn't any setup left."
            confirmAddition.visibility = View.GONE
        }

        occurringProblemViewModel.listOccurringProblem.observe(viewLifecycleOwner) {
            adapterAddOccurringProblems = AddNewOccurringProblemAdapter(listSetupsWithoutProblem)
            val linearLayoutManagerAddProblem = LinearLayoutManager(this.context)
            recyclerViewAddOccurringProblem.layoutManager = linearLayoutManagerAddProblem
            recyclerViewAddOccurringProblem.adapter = adapterAddOccurringProblems
        }

        /*
        The confirm button check weather if a checkbox inside the recycler view
        isChecked or not using a map created inside the adapter itself.
        If it's checked then it reads first the code of the setup inside listSetupAddOccurringProblem
        in the same position of the checkbox checked and, then, it reads the value of the description
        given using, as for the checkBox, the map created inside the adapter that keeps updated
        all the descriptions for every setup shown.
         */
        confirmAddition.setOnClickListener {

            adapterAddOccurringProblems.getListCheckedElements().filter {
                it.value // this value is set to true when the a checkbox is clicked
            }.forEach {
                /*
                Foreach setup linked to a checked checkBox it adds directly to the repository,
                calling a method of the adapter first and a method of the viewModel afterwards,
                the new problem created thanks to the description edit text that appears whenever
                the user checks a checkbox.
                 */
                val newOccurringProblem =
                    DataOccurringProblem(
                        problemClicked.code,
                        it.key.code,
                        adapterAddOccurringProblems.getListDescriptionElements().getValue(it.key)
                )
                occurringProblemAdapter.addItemToItemView(newOccurringProblem)
            }
            Toast.makeText(this.context, "New setups facing the problem added", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        closeAddition.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    /*
    Whenever the user clicks on removeItem the dialog box shows up
    after the call on showDialogRemovingProblem method.
     */
    override fun onProblemSolvedClick(element: DataOccurringProblem, itemView: View) {
        showDialogRemovingProblem(element, itemView)
    }

    /*
    Create a dialog box that lets the user add a description
    if the he clicks on reappeared problem
     */
    private fun showDialogRemovingProblem(element: DataOccurringProblem, view: View) {
        val dialog = Dialog(view.context)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_all_uses)

        val descriptionNewSolvedProblem = dialog.findViewById(R.id.editTextAllUses) as EditText
        val changeableText = dialog.findViewById(R.id.textViewChangeableAllUses) as TextView

        changeableText.text = "If the problem is solved click on confirm otherwise click on cancel.\nDescription value is not compulsory."

        val confirmReappearedProblem = dialog.findViewById(R.id.confirmFrameAllUses) as FrameLayout
        val cancelReappearedProblem = dialog.findViewById(R.id.cancelFrameAllUses) as FrameLayout

        /*
        Confirm that the problem that was solved before reappeared on the same setup.
         */
        confirmReappearedProblem.setOnClickListener {
            /*
            Calls the method removeItemFromList of the adapter and pass
            the element clicked and the description of the future problemSolved.
             */
            occurringProblemAdapter.removeItemFromList(
                element,
                descriptionNewSolvedProblem.text.toString()
            )
            dialog.dismiss()
        }
        cancelReappearedProblem.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

     /*
    Returns a list in which all the different setups are not already stored inside
    listOccurringProblem.
    So it analyzes all the setups stored and then it returns the ones that are not inside
    the list mentioned 2 lines above. Moreover checks if the setup has already fixed the
    problem.
     */
    private fun findSetupsWithoutProblem(): MutableList<DataSetup>{
        val listSetupsWithoutProblem= mutableListOf<DataSetup>()

        setupViewModel.setupList.value?.forEachIndexed { _, dataSetup ->
            if(occurringProblemViewModel.listOccurringProblem.value?.filter {
                    dataSetup.code == it.setupCode &&
                            it.problemCode == problemClicked.code
                }?.size == 0 &&
                    solvedProblemViewModel.listSolvedProblem.value?.filter {
                        dataSetup.code == it.setupCode &&
                                it.problemCode == problemClicked.code
                    }?.size == 0) {
                /*
                Adds to the list the setups that are not currently linked to a solved and occurring
                problem having problem code equals to the problem code of the clicked one.
                 */
                listSetupsWithoutProblem.add(dataSetup)
            }
        }
        return listSetupsWithoutProblem
    }

}