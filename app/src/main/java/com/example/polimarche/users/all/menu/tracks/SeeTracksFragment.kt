package com.example.polimarche.users.all.menu.tracks

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentGeneralTracksSeeTracksBinding
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.track.TracksViewModel


class SeeTracksFragment : Fragment(R.layout.fragment_general_tracks_see_tracks){

    private val tracksViewModel: TracksViewModel by viewModels()

    private var _binding: FragmentGeneralTracksSeeTracksBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchView: SearchView

    private lateinit var seeTracksRecyclerView: RecyclerView
    private lateinit var seeTracksAdapter: SeeTracksAdapter

    private val trackList: MutableLiveData<MutableList<DataTrack>> = MutableLiveData()
    private val inputQuery: MutableLiveData<String?> = MutableLiveData("")
    private var originalTrackList: List<DataTrack> = emptyList()


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

        tracksViewModel.listTracks.observe(viewLifecycleOwner) {
            seeTracksAdapter = SeeTracksAdapter(tracksViewModel)

            seeTracksRecyclerView = binding.listTracks
            val linearLayoutManager = LinearLayoutManager(this.context)
            seeTracksRecyclerView.layoutManager = linearLayoutManager
            seeTracksRecyclerView.adapter = seeTracksAdapter
        }


        tracksViewModel.listTracks.observe(viewLifecycleOwner) { newList ->
            originalTrackList = newList
            trackList.value = originalTrackList.toMutableList()
        }

        trackList.observe(viewLifecycleOwner, Observer { tracks ->
            seeTracksAdapter.setNewList(tracks)
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    setQuery(newText)
                }
                return true
            }
        })

        binding.imageButtonAddTrack.setOnClickListener {
            showAddTrackDialog()
        }

    }

    private fun setQuery(query: String) {
        inputQuery.value = query
        filterList()
    }


    private fun filterList() {
        val query = inputQuery.value.toString().trim() // Rimuovi eventuali spazi bianchi prima e dopo la query
        if (query.isNotBlank()) {
            val filteredList = originalTrackList.filter { track ->
                track.name.contains(query, ignoreCase = true) // Filtra in base al nome della traccia ignorando maiuscole/minuscole
            }
            trackList.value = filteredList.toMutableList()
        } else {
            trackList.value = originalTrackList.toMutableList()
        }
    }



    private fun showAddTrackDialog(){
        val dialog = Dialog(requireContext())
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_add_track)

        val newTrackName = dialog.findViewById(R.id.editTextAddTrackName) as EditText
        val newTrackLength = dialog.findViewById(R.id.editTextAddTrackLength) as EditText
        val descriptionText = dialog.findViewById(R.id.textViewChangeableAddTracks) as TextView

        newTrackLength.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL


        val confirmFrame = dialog.findViewById(R.id.confirmFrameAddTracks) as FrameLayout
        val cancelFrame = dialog.findViewById(R.id.cancelFrameAddTracks) as FrameLayout

        /*
        Confirm to add a new track
         */
        confirmFrame.setOnClickListener {
            descriptionText.text = ""

            /*
            Searches if a saved track has the same name as the one inserted.
             */
            if ( tracksViewModel.listTracks.value?.filter {
                    newTrackName.text.toString().lowercase() == it.name.lowercase()
                }?.size != 0){
                descriptionText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_700))
                descriptionText.append("${newTrackName.text} already exists.")
            }
            /*
                Whenever the user inserts a value that cannot be converted to a Double value,
                the dialog won't be dismissed and changes also the color and the text of a textView
                in which there's written what the error is.
                 */
            else if ( newTrackLength.text.toString().toDoubleOrNull() == null ){
                descriptionText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_700))
                descriptionText.append("\nThe value inside the edit text must be changed to confirm the modification. " +
                        "\nWrite a decimal number like 6.092 if the track length is 6.092km")
            }
            else {
                /*
                The new track name should be saved with only the first letter in uppercase
                and all the others in lowercase
                 */
                var trackName = ""
                newTrackName.text.toString().forEachIndexed { index, c ->
                    trackName += if (
                        index==0
                    ) c.toString().uppercase()
                    else c.toString().lowercase()
                }

                val newTrack = DataTrack(
                    trackName,
                    newTrackLength.text.toString().toDouble()
                )
               // seeTracksAdapter.addNewTrack(newTrack)
                dialog.dismiss()
            }
        }
        cancelFrame.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}