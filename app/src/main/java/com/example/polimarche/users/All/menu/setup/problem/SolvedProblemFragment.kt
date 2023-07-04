package com.example.polimarche.users.all.menu.setup.problem

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentGeneralSetupSolvedProblemBinding
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.solved_problem.DataSolvedProblem
import com.example.polimarche.data_container.problem.solved_problem.SolvedProblemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SolvedProblemFragment(
    private val problemClicked: DataProblem,
    window: Window
): Fragment(R.layout.fragment_general_setup_solved_problem),
    SolvedProblemAdapter.OnProblemReappearedClick {

    private val solvedProblemViewModel: SolvedProblemViewModel by viewModels()

    private var _binding: FragmentGeneralSetupSolvedProblemBinding? = null
    private val binding get() = _binding!!

    private val window = window

    private lateinit var solvedProblemAdapter: SolvedProblemAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralSetupSolvedProblemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)


        solvedProblemViewModel.listSolvedProblem.observe(viewLifecycleOwner) { solvedProblems ->
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            solvedProblemAdapter = SolvedProblemAdapter(
                problemClicked,
                solvedProblemViewModel,
                this
            )
            recyclerView = binding.listSolvedProblemSetup
            val linearLayoutManager = LinearLayoutManager(this.context)
            recyclerView.adapter = solvedProblemAdapter
            recyclerView.layoutManager = linearLayoutManager
        }
    }


    override fun onProblemReappearedClick(
        element: DataSolvedProblem,
        itemView: View
    ) {
        showReappearedProblemDialog(element, itemView)
    }




    /*
    If the users click on the imageVew, a dialog box pops up and, inside
    the dialog, the user can put a description of the problem again.
    Then if the user confirms the reappearance the element will be removed.
    */
    private fun showReappearedProblemDialog(element: DataSolvedProblem, view: View) {
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

        val descriptionNewOccurringProblem = dialog.findViewById(
            R.id.editTextAllUses
        ) as EditText

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
            CoroutineScope(Dispatchers.Main).launch {
                solvedProblemAdapter.removeItemFromList(
                    element,
                    descriptionNewOccurringProblem.text.toString()
                )
            }
            dialog.dismiss()
        }
        cancelReappearedProblem.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onResume() {
        super.onResume()

        solvedProblemViewModel.initialize()
    }


}