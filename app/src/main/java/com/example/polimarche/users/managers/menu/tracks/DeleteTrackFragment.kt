package com.example.polimarche.users.managers.menu.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentManagersTracksDeleteTrackBinding
import com.example.polimarche.data_container.track.TracksViewModel
import com.example.polimarche.users.all.menu.tracks.SeeTracksAdapter

class DeleteTrackFragment : Fragment(R.layout.fragment_managers_tracks_delete_track){

    private var _binding: FragmentManagersTracksDeleteTrackBinding? = null
    private val binding get() = _binding!!

    private val tracksViewModel: TracksViewModel by viewModels()

    private lateinit var searchViewDeleteTrack: SearchView

    private lateinit var recyclerViewDeleteTracks: RecyclerView
    private lateinit var adapterDeleteTrack: DeleteTracksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersTracksDeleteTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewDeleteTrack = binding.searchViewDeleteTracks

        tracksViewModel.listTracks.observe(viewLifecycleOwner, Observer { tracks ->
            recyclerViewDeleteTracks = binding.listTracks
            val linearLayoutManager = LinearLayoutManager(this.context)
            recyclerViewDeleteTracks.layoutManager = linearLayoutManager
            adapterDeleteTrack = DeleteTracksAdapter(tracksViewModel)
            recyclerViewDeleteTracks.adapter = adapterDeleteTrack
        })



        searchViewDeleteTrack.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    setQuery(query)
                }
                searchViewDeleteTrack.clearFocus()
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
        adapterDeleteTrack.setQuery(query!!)
    }

    private fun filterList(){
        adapterDeleteTrack.filterNameByQuery()
    }
}