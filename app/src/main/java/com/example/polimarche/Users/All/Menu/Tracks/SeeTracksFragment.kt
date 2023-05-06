package com.example.polimarche.Users.All.Menu.Tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralTracksSeeTracksBinding
import com.example.polimarche.Users.All.Adapters.SeeTracksAdapter

class SeeTracksFragment : Fragment(R.layout.fragment_general_tracks_see_tracks){

    private val tracksViewModel: TracksViewModel by viewModels()

    private var _binding: FragmentGeneralTracksSeeTracksBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchView: SearchView

    private lateinit var seeTracksRecyclerView: RecyclerView
    private lateinit var seeTracksAdapter: SeeTracksAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralTracksSeeTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchViewSeeTracks

        seeTracksAdapter = SeeTracksAdapter(tracksViewModel)

        seeTracksRecyclerView = binding.listTracks
        val linearLayoutManager = LinearLayoutManager(this.context)
        seeTracksRecyclerView.layoutManager = linearLayoutManager
        seeTracksRecyclerView.adapter = seeTracksAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    setQuery(query)
                }
                searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    setQuery(newText)
                    filterList()
                }
                return true
            }
        })
    }

    private fun setQuery(query: String?){
        seeTracksAdapter.setQuery(query!!)
    }

    private fun filterList(){
        seeTracksAdapter.filterNameByQuery()
    }

}