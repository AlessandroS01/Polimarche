package com.example.polimarche.Users.All.Menu.Setup.Problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralManageProblemsSetupBinding
import com.example.polimarche.Data.DataProblem

class ManageProblemFragment(
    private val problemClicked: DataProblem
) : Fragment(R.layout.fragment_general_manage_problems_setup){

    private val problemsViewModel: ProblemsViewModel by viewModels()

    private var _binding: FragmentGeneralManageProblemsSetupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralManageProblemsSetupBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.problemCodeTextView.text = "Problem code: ${problemClicked.code}"
        binding.descriptionProblemTextView.text = "Problem description: ${problemClicked.description}"

        binding.closeButtonManageProblemsSetup.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        /*
        Changes the fragment to visualize inside the frame of the current fragment.
         */
        val occurringProblemFragment = OccurringProblemFragment(problemClicked)
        val solvedProblemFragment = SolvedProblemFragment(problemClicked)
        parentFragmentManager.beginTransaction().replace(binding.frameLayoutManageProblem.id
            , occurringProblemFragment).commit()
        binding.radioGroupManageProblem.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.radioButtonManageProblemSolved.id){
                parentFragmentManager.beginTransaction().replace(binding.frameLayoutManageProblem.id,
                    solvedProblemFragment).commit()
            }
            else {
                parentFragmentManager.beginTransaction().replace(binding.frameLayoutManageProblem.id,
                    occurringProblemFragment).commit()
            }
        }
    }
}