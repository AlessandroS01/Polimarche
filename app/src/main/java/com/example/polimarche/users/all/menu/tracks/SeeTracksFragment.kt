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
import com.example.polimarche.users.managers.menu.tracks.DeleteTracksAdapter


class SeeTracksFragment : Fragment(R.layout.fragment_general_tracks_see_tracks){

    private val tracksViewModel: TracksViewModel by viewModels()

    // Variabili utilizzate per eseguire il binding degli elementi del layout
    // fragment_general_tracks_see_tracks utilizzando FragmentGeneralTracksSeeTracksBinding
    private var _binding: FragmentGeneralTracksSeeTracksBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchView: SearchView //variabile che verrà inizializzata in un
                                                // momento successivo rispetto alla sua dichiarazione

    private lateinit var seeTracksRecyclerView: RecyclerView
    private lateinit var seeTracksAdapter: SeeTracksAdapter

    private val trackList: MutableLiveData<MutableList<DataTrack>> = MutableLiveData()

    /*
        Usata per osservare il valore della query creata dalla searchView quando
        un utente scrive su di essa.
    */
    private val inputQuery: MutableLiveData<String?> = MutableLiveData("")

    // Lista immutabile di oggetti DataTrack che rappresenta la lista originale di tutte le tracks
    private var originalTrackList: List<DataTrack> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneralTracksSeeTracksBinding.inflate(inflater, container, false)
        // La funzione inflate prende il layout XML del fragment_general_tracks_see_tracks
        // e lo converte in un oggetto FragmentGeneralTracksSeeTracksBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchViewSeeTracks

        tracksViewModel.initialize()

        //Osservatore sulla lista di tracce (listTracks) all'interno del tracksViewModel
        tracksViewModel.listTracks.observe(viewLifecycleOwner) {tracks ->
            seeTracksAdapter = SeeTracksAdapter(tracksViewModel)

            seeTracksRecyclerView = binding.listTracks
            val linearLayoutManager = LinearLayoutManager(this.context)
            seeTracksRecyclerView.layoutManager = linearLayoutManager
            seeTracksRecyclerView.adapter = seeTracksAdapter

            // Notify the adapter when the list of tracks changes
            seeTracksAdapter.setNewList(tracks)
        }

        // Observer su listTracks
        tracksViewModel.listTracks.observe(viewLifecycleOwner) { newList ->
            // Quando listTracks viene modificata
            originalTrackList = newList
            trackList.value = originalTrackList.toMutableList()

            // Viene chiamato il metodo setNewList dell'adapter passando la lista delle tracce originali convertita
            // in una lista modificabile
            (originalTrackList as MutableList<DataTrack>?)?.let { seeTracksAdapter.setNewList(it) }
        }

        // Observer su trackList
        trackList.observe(viewLifecycleOwner, Observer { tracks ->
            seeTracksAdapter.setNewList(tracks)
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Quando l'utente invia la query di ricerca, si rimuove il focus dalla SearchView.
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            // Quando il testo nella SearchView cambia, se il nuovo testo non è nullo,
            // viene chiamato il metodo setQuery passando il nuovo testo per gestire la ricerca
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
        val query = inputQuery.value.toString().trim() // Viene ottenuta la query rimuovendo eventuali spazi bianchi prima e dopo la query
        if (query.isNotBlank()) { // Se la query non è vuota
            val filteredList = originalTrackList.filter { track ->
                track.name.contains(query, ignoreCase = true) //  La lambda function verifica se il nome della traccia contiene la query di ricerca,
                                                                            // ignorando la differenza tra maiuscole e minuscole
            }
            trackList.value = filteredList.toMutableList()
        } else {
            trackList.value = originalTrackList.toMutableList()
        }
    }



    private fun showAddTrackDialog(){
        val dialog = Dialog(requireContext())
        // Proprietà del Dialog
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_add_track)

        // Vengono ottenute le referenze alle diverse viste all'interno del layout del dialog
        val newTrackName = dialog.findViewById(R.id.editTextAddTrackName) as EditText
        val newTrackLength = dialog.findViewById(R.id.editTextAddTrackLength) as EditText
        val descriptionText = dialog.findViewById(R.id.textViewChangeableAddTracks) as TextView

        // Indica che l'input accettato deve essere un numero decimale.
        newTrackLength.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL

        val confirmFrame = dialog.findViewById(R.id.confirmFrameAddTracks) as FrameLayout
        val cancelFrame = dialog.findViewById(R.id.cancelFrameAddTracks) as FrameLayout

        /*
        Bottone di conferma per modificare la lunghezza della traccia
         */
        confirmFrame.setOnClickListener {
            descriptionText.text = ""

            /*
            Metodo che verifica se esiste già una traccia con lo stesso nome all'interno di listTracks
             */
            if ( tracksViewModel.listTracks.value?.filter {
                    newTrackName.text.toString().lowercase() == it.name.lowercase()
                }?.size != 0){
                descriptionText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_700))
                descriptionText.append("${newTrackName.text} already exists.")
            }
            /*
            Ogni volta che l'utente inserisce un valore che non può essere convertito in un valore Double,
            la finestra di dialogo non verrà chiusa e cambia anche il colore e il testo di una textView
            in cui c'è scritto qual è l'errore.
                 */
            else if ( newTrackLength.text.toString().toDoubleOrNull() == null ){
                descriptionText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_700))
                descriptionText.append("\nThe value inside the edit text must be changed to confirm the modification. " +
                        "\nWrite a decimal number like 6.092 if the track length is 6.092km")
            }
            else {
                /*
                Il nuovo nome della traccia deve essere salvato con solo la prima lettera maiuscola
                e tutti gli altri in minuscolo
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
                    newTrackLength.text.toString()
                )

                seeTracksAdapter.addNewTrack(newTrack)
                dialog.dismiss()
            }
        }
        // Bottone per annullamento modifiche
        cancelFrame.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}