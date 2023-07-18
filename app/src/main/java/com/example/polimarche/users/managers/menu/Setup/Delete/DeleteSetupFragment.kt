package com.example.polimarche.users.managers.menu.setup.delete

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
import com.example.polimarche.databinding.FragmentManagersSetupDeleteSetupBinding
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel

/*
    Fragment used to show all the setup and their parameters that, unlike
    the fragment seeSetup, add the possibility to delete the setups.
 */
class DeleteSetupFragment(window: Window) : Fragment(R.layout.fragment_managers_setup_delete_setup),
    DeleteSetupAdapter.OnSetupCodeClickListener{

    private val setupViewModel: SetupViewModel by viewModels()

    private val window = window

    private lateinit var searchView: SearchView
    private lateinit var adapter: DeleteSetupAdapter
    private lateinit var recyclerViewDeleteSetup: RecyclerView

    private var _binding: FragmentManagersSetupDeleteSetupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagersSetupDeleteSetupBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        searchView = binding.searchViewSetupDelete

        adapter = DeleteSetupAdapter(setupViewModel, this)

        setupViewModel.setupList.observe(viewLifecycleOwner) {problem ->
            recyclerViewDeleteSetup = binding.deleteSetupList
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            val layoutManager = LinearLayoutManager(context)

            recyclerViewDeleteSetup.layoutManager = layoutManager
            adapter = DeleteSetupAdapter(setupViewModel, this)
            recyclerViewDeleteSetup.adapter = adapter

            adapter.setNewList(problem)
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

    /*
    Metodo usato dalla search view che permette all'adapter di mostrare gli item che matchano
    con l'input dato.
     */
    private fun filterList(query: String?){
        if(query != null){
            val filteredList = setupViewModel.setupList.value?.filter { query in it.code.toString() }!!
            adapter.setNewList(filteredList.toMutableList())
        }
    }

    /*
    Override the method contained inside the interface OnSetupCodeClickListener
    that let the user see the details of the setup clicked.
    Add the possibility to delete the setup.
     */
    override fun onSetupCodeClickListener(setupClicked: DataSetup) {

        /*
        Viene passato il codice del setup cliccato al visualizeSetupFragment.
         */
        val bundle = Bundle()
        bundle.putInt("SETUP_CODE", setupClicked.code)
        val visualizeSetup = VisualizeSetupFragment(adapter, window)
        visualizeSetup.arguments = bundle

        parentFragmentManager.beginTransaction().apply{
            replace(binding.frameLayoutDeleteSetupVisualizeSetup.id, visualizeSetup).commit()
        }
    }

    override fun onResume() {
        super.onResume()

        setupViewModel.initialize()

    }

}