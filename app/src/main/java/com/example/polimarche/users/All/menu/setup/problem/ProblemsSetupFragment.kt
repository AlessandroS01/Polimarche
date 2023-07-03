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
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentGeneralSetupProblemsSetupBinding
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.ProblemsViewModel

class ProblemsSetupFragment : Fragment(R.layout.fragment_general_setup_problems_setup),
    ProblemAdapter.OnManageProblemClick {

    private val problemViewModel: ProblemsViewModel by viewModels()

    private var _binding: FragmentGeneralSetupProblemsSetupBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchView: SearchView

    private lateinit var problemAdapter: ProblemAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneralSetupProblemsSetupBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchViewProblemSetup
        problemViewModel.initialize()

        problemViewModel.listProblems.observe(viewLifecycleOwner) {problem ->
            problemAdapter = ProblemAdapter(
                problemViewModel.listProblems.value?.toMutableList()!!,
                this
            )
            recyclerView = binding.problemSetupList
            val layoutManager = LinearLayoutManager(this.context)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = problemAdapter

            problemAdapter.setList(problem)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })


        binding.imageButtonAddProblem.setOnClickListener {
            showDialog()
        }

    }

    /*
    Method used by the search view that allows the adapter to show the items
    that matches with the given input.
    Since the description and the input can be given in uppercase or lowercase,
    the comparison between the query and the saved description is made in lowercase.
     */
    private fun filterList(query: String?){
        if(query != null){
            val filteredList = problemViewModel.listProblems.value?.toMutableList()!!.filter {
                query.lowercase() in it.description.lowercase()
            }
            problemAdapter.setFilteredList(filteredList.toMutableList())
        }
    }

    /*
    Create a dialog box that let the user add a new setup problem
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
        dialog.setContentView(R.layout.dialog_box_general_add_setup_problem)

        val errorText = dialog.findViewById(R.id.problemDescriptionError) as TextView
        errorText.visibility = View.GONE

        val newDescription = dialog.findViewById(R.id.editTextNewProblemDescription) as EditText

        val confirmAddNewProblem = dialog.findViewById(R.id.confirmAddNewProblem) as FrameLayout
        val cancelAddNewProblem = dialog.findViewById(R.id.cancelAddNewProblem) as FrameLayout

        /*
        Control if a problem inserted already exists or not.
        The value of correctness boolean variable is set to false if the problem
        already exists or the user has not inserted nothing inside the edit text.
        During the confirm then, there's a check on the value of correctness.
         */
        var correctness = false
        newDescription.addTextChangedListener {
            if(newDescription.text.isEmpty()) {
                errorText.visibility = View.GONE
            }
            else if (problemViewModel.listProblems.value?.toMutableList()!!.any {
                    newDescription.text.toString().lowercase() in it.description.lowercase()
                    }){
                        correctness = false
                        errorText.visibility = View.VISIBLE
            } else {
                errorText.visibility = View.GONE
                correctness = true
            }
        }
        /*
        Confirm the add of the new problem inside the list and the call to the
        adapter to let it know that the item was added.
         */
        confirmAddNewProblem.setOnClickListener {
            if (correctness) {
                var newCode = 1
                while (
                    problemViewModel.listProblems.value?.toMutableList()!!.filter {
                        it.code == newCode
                    }.isNotEmpty()
                ){
                    newCode++
                }

                val newProblem = DataProblem(
                    newCode,
                    newDescription.text.toString()
                )
                problemViewModel.addNewProblem(newProblem)
                problemAdapter.setList(
                    problemViewModel.listProblems.value?.toMutableList()!!
                )
                dialog.dismiss()
            }
        }
        cancelAddNewProblem.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onManageProblemClick(problemClicked: DataProblem) {
        searchView.visibility = View.GONE

        binding.imageButtonAddProblem.visibility = View.GONE
        val manageProblemFragment = ManageProblemFragment(problemClicked, searchView, binding.imageButtonAddProblem)
        parentFragmentManager.beginTransaction().replace(binding.frameLayoutSetupManageProblemSetup.id,
            manageProblemFragment).commit()

    }
}