package com.example.polimarche.Users.All.Menu.Setup.Problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralSetupSolvedProblemBinding
import com.example.polimarche.Data.DataSolvedProblem
import com.example.polimarche.Users.All.Adapters.SolvedProblemAdapter

class SolvedProblemFragment: Fragment(R.layout.fragment_general_setup_solved_problem) {

    private var _binding: FragmentGeneralSetupSolvedProblemBinding? = null
    private val binding get() = _binding!!

    private val listProblemSolved: MutableList<DataSolvedProblem> = initializeProblemSolved()

    private lateinit var adapter: SolvedProblemAdapter
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

        recyclerView = binding.listSolvedProblemSetup
        val linearLayoutManager = LinearLayoutManager(this.context)
        adapter = SolvedProblemAdapter(listProblemSolved)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

    }

    private fun initializeProblemSolved(): MutableList<DataSolvedProblem>{
        return mutableListOf(
            DataSolvedProblem(1, 1, "Risolto posteriore"),
            DataSolvedProblem(1, 2, "Risolto anteriore"),
            DataSolvedProblem(1, 3, "Risolto posteriore"),
            DataSolvedProblem(1, 4, "Risolto posteriore"),
            DataSolvedProblem(1, 5, "Risolto posteriore"),
            DataSolvedProblem(1, 6, "Risolto posteriore"),
            DataSolvedProblem(1, 7, "Risolto posteriore"),
        )
    }

}