package com.example.polimarche.Users.All.Menu.Setup.Problem

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralSetupSolvedProblemBinding
import com.example.polimarche.Data.DataOccurringProblem
import com.example.polimarche.Data.DataProblem
import com.example.polimarche.Data.DataSolvedProblem
import com.example.polimarche.Users.All.Adapters.SolvedProblemAdapter

class SolvedProblemFragment(
    private val problemClicked: DataProblem
): Fragment(R.layout.fragment_general_setup_solved_problem), SolvedProblemAdapter.OnProblemReappearedClick {

    private val solvedProblemViewModel: SolvedProblemViewModel by viewModels()

    private var _binding: FragmentGeneralSetupSolvedProblemBinding? = null
    private val binding get() = _binding!!

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
        dialog.setContentView(R.layout.dialog_box_general_problem_reappeared_or_solved)

        val descriptionNewOccurringProblem = dialog.findViewById(
            R.id.editTextReappearedProblem
        ) as EditText

        val confirmReappearedProblem = dialog.findViewById(R.id.confirmReappearedProblem) as FrameLayout
        val cancelReappearedProblem = dialog.findViewById(R.id.cancelReappearedProblem) as FrameLayout

        /*
        Confirm that the problem that was solved before reappeared on the same setup.
         */
        confirmReappearedProblem.setOnClickListener {
            /*
            Calls the method removeItemFromList of the adapter and pass
            the element clicked and the description of the future problemSolved.
             */
            solvedProblemAdapter.removeItemFromList(
                element,
                descriptionNewOccurringProblem.text.toString()
            )
            dialog.dismiss()
        }
        cancelReappearedProblem.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}