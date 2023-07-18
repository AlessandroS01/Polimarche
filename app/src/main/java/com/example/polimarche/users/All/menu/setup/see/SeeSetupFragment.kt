package com.example.polimarche.users.all.menu.setup.see

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentGeneralSetupSeeSetupBinding
import com.example.polimarche.data_container.setup.SetupViewModel

class SeeSetupFragment(window: Window) : Fragment(R.layout.fragment_general_setup_see_setup){

    private val setupViewModel: SetupViewModel by viewModels()

    private val window = window

    private lateinit var searchView: SearchView
    private lateinit var adapter: SeeSetupAdapter
    private lateinit var recyclerView: RecyclerView

    private var _binding: FragmentGeneralSetupSeeSetupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneralSetupSeeSetupBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Impostare la finestra corrente in uno stato in cui gli input
        // utente non sono intercettabili o gestiti
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        searchView = binding.searchViewSetupSee

        adapter = SeeSetupAdapter(setupViewModel)

        recyclerView = binding.seeSetupList
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        setupViewModel.setupList.observe(viewLifecycleOwner) {

            // Ripristinare la normale interattività agli input utente
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            adapter = SeeSetupAdapter(setupViewModel)
            recyclerView.adapter = adapter

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

    }

    private fun filterList(query: String?){
        if(query != null){
            // Viene utilizzata una lambda function per definire il criterio di filtro
            // (query in it.toString())
            // La lambda function verifica se la query è
            // contenuta nella rappresentazione in stringa di ciascun oggetto.
            val filteredList = setupViewModel.getSetupCodes().filter { query in it.toString() }
            adapter.setFilteredList(filteredList.toMutableList())
        }
    }
}