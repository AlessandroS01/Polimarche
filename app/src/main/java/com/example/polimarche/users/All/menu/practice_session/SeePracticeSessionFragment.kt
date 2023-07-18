package com.example.polimarche.users.all.menu.practice_session


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentGeneralPracticeSessionSeePracticeSessionBinding
import com.example.polimarche.data_container.practice_session.PracticeSessionViewModel
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.users.all.menu.tracks.SeeTracksAdapter

class SeePracticeSessionFragment : Fragment(
    R.layout.fragment_general_practice_session_see_practice_session
){

    // Variabili utilizzate per eseguire il binding degli elementi del layout
    // fragment_general_practice_session_see_pratice_session
    // utilizzando FragmentPracticeSessionSeePracticeSessionBinding
    private var _binding: FragmentGeneralPracticeSessionSeePracticeSessionBinding? = null
    private val binding get() = _binding!!

    private val practiceSessionViewModel: PracticeSessionViewModel by viewModels()

    //variabili che verranno inizializzate in un momento successivo alla loro dichiarazione
    private lateinit var searchView: SearchView
    private lateinit var recyclerViewSeePracticeSession: RecyclerView
    private lateinit var adapterPracticeSession: SeePracticeSessionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralPracticeSessionSeePracticeSessionBinding.inflate(
            inflater,
            container,
            false
        )
        // La funzione inflate prende il layout XML del fragment_general_practice_session_see_pratice_session
        // e lo converte in un oggetto FragmentPracticeSessionSeePracticeSessionBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchForEventCategoryCheckBox = binding.checkBoxEventCategories
        val radioGroupEventCategories = binding.radioGroupEventCategories
        radioGroupEventCategories.visibility = View.GONE

        searchView = binding.searchViewPracticeSession
        /*
        Utilizzato per impostare l'hint per la query su Practice Session date \"YYYY-MM-DD\""
         */
        searchView.queryHint = setQueryHintSearchView(0)

        practiceSessionViewModel.initialize() // inizializzare il practiceSessionViewModel

        // Impostazione un observer sull'oggetto LiveData listPracticeSession
        // all'interno del practiceSessionViewModel.
        // L'observer viene notificato ogni volta che il valore di listPracticeSession cambia.
        practiceSessionViewModel.listPracticeSession.observe(viewLifecycleOwner) {
            adapterPracticeSession = SeePracticeSessionAdapter(practiceSessionViewModel)

            recyclerViewSeePracticeSession = binding.listPracticeSession
            val linearLayoutManager = LinearLayoutManager(this.requireContext())
            recyclerViewSeePracticeSession.layoutManager = linearLayoutManager
            recyclerViewSeePracticeSession.adapter = adapterPracticeSession
        }

        /*
        Imposta la visibilità del radio button su NONE ogni volta che la casella selezionata
        è deselezionata.
        Imposta anche l'hint per la query di searchView.
         */
        searchForEventCategoryCheckBox.setOnCheckedChangeListener { _, isChecked ->
            // Se il checkbox è selezionato, il gruppo di radiobutton viene reso visibile,
            // altrimenti viene reso invisibile.
            radioGroupEventCategories.visibility = if (isChecked) View.VISIBLE else View.GONE

            /* Imposta l'hint per la query nel SearchView a seconda dello stato del checkbox.
               Se il checkbox non è selezionato, l'hint viene impostato come
               "Practice session date "YYYY-MM-DD"".
               Altrimenti, viene richiamato il metodo setQueryHintSearchView()
               per impostare l'hint in base al radiobutton
               selezionato nel radioGroupEventCategories. */
            searchView.queryHint = if (!isChecked) "Practice session date \"YYYY-MM-DD\"" else {
                setQueryHintSearchView(radioGroupEventCategories.checkedRadioButtonId)
            }
            /*
            Se la lunghezza della query è 10 viene richiamato il metodo setQueryFilterByEventCategory()
            per filtrare gli elementi in base alla data inserita nella query
            */
            if(searchView.query.length == 10){
                setQueryFilterByEventCategory(searchView.query.toString())
            }
        }
        /*
        Modifica l'hint per la query sulla modifica del radioButton selezionato
         */
        radioGroupEventCategories.setOnCheckedChangeListener { _, checkedId ->
            searchView.queryHint = setQueryHintSearchView(checkedId)
            /*
            Se la lunghezza della query è 10 viene richiamato il metodo setQueryFilterByEventCategory()
            per filtrare gli elementi in base alla data inserita nella query
            */
            if(searchView.query.length == 10){
                setQueryFilterByEventCategory(searchView.query.toString())
            }
        }



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                /*
                Blocca l'invio ogni volta che la query contiene almeno una lettera e la
                lunghezza è inferiore a 10.
                 */

                //none ritorna true se query non matcha le due condizioni
                return if(query != null && query.none{ it.isLetter() } && query.length == 10){
                    searchView.clearFocus()
                    setQueryFilterByEventCategory(query)
                    true
                } else false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                /*
                Ripristina l'elenco predefinito.
                 */
                adapterPracticeSession.restoreListToDefault()
                if (newText != null) {
                    /*
                    Impedisci alla query di avere una lunghezza superiore a 10.
                     */
                    if(newText.length>10){
                        //restituisce una sottostringa dei primi 10 caratteri del testo newText
                        searchView.setQuery(newText.substring(0, 10), false)
                        //Passando false, l'ascoltatore non verrà chiamato in questo caso.
                    }

                    if(newText.none { // Verifica se il nuovo testo inserito non
                            // contiene alcuna lettera
                            it.isLetter()
                        }
                    ){
                        searchView.findViewById<EditText>( // Se il testo è composto solo da
                                                            // numeri o caratteri speciali,
                                            // il colore del testo viene impostato su bianco
                            androidx.appcompat.R.id.search_src_text
                        ).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        // Controlla se la lunghezza del testo è di 4 o 7 caratteri.
                        // Se questa condizione è vera, il testo viene formattato
                        // aggiungendo un trattino ("-") dopo il quarto carattere o
                        // dopo il settimo carattere.
                        if(newText.length == 4 || newText.length == 7){
                            searchView.setQuery("$newText-", false)
                        }
                        return true
                    }
                    else { // Se il testo contiene almeno una lettera e il colore
                        // viene settato a rosso
                        searchView.findViewById<EditText>(
                            androidx.appcompat.R.id.search_src_text
                        ).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                        return false
                    }
                }
                return false
            }
        })
    }

    /*
    Restituisce l'hint per la query all'interno della searchView in base al radioButton selezionato.
     */
    private fun setQueryHintSearchView(checkedId: Int): String{
        when(checkedId){
            binding.radioButtonEndurance.id ->{
                    return "Endurance session date \"YYYY-MM-DD\""
            }
            binding.radioButtonAcceleration.id ->{
                return "Acceleration session date \"YYYY-MM-DD\""
            }
            binding.radioButtonAutocross.id ->{
                return "Autocross session date \"YYYY-MM-DD\""
            }
            binding.radioButtonSkidpad.id->{
                return "Skidpad session date \"YYYY-MM-DD\""
            }
        }
        return "Practice session date \"YYYY-MM-DD\""
    }

    /*
    Metodo che controlla se il checkBox è spuntato e, nel caso,
    quale radio button è selezionato.
    Restituisce 0 quando il checkBox non è selezionato.
    Se il checkBox è selezionato restituisce il radioButton id.
     */
    private fun findRadioButtonChecked(): Int{
        if(binding.checkBoxEventCategories.isChecked){
            when(binding.radioGroupEventCategories.checkedRadioButtonId){
                binding.radioButtonEndurance.id ->{
                    return binding.radioButtonEndurance.id
                }
                binding.radioButtonAcceleration.id ->{
                    return binding.radioButtonAcceleration.id
                }
                binding.radioButtonAutocross.id ->{
                    return binding.radioButtonAutocross.id
                }
                binding.radioButtonSkidpad.id->{
                    return binding.radioButtonSkidpad.id
                }
            }
        }
        return 0
    }

    /*
    Verifica se il radio button è selezionato e filtra gli elementi in base alla data
    inserita nella query.
    */
    private fun setQueryFilterByEventCategory(query: String){
        when(findRadioButtonChecked()){
            // Se l'ID è 0, viene chiamato il metodo sull'adapter e gli elementi vengono
            // filtrati per data senza considerare la categoria dell'evento
            0->{
                adapterPracticeSession.filterListWithoutEvents(query)
            }

            /*Se l'ID corrisponde all'ID del radiobutton "Endurance", viene chiamato il metodo
           sull'adapter per filtrare gli elementi per data e categoria "Endurance". */
            binding.radioButtonEndurance.id ->{
                adapterPracticeSession.filterListByEventChecked(query, "Endurance")
            }
            binding.radioButtonAcceleration.id ->{
                adapterPracticeSession.filterListByEventChecked(query, "Acceleration")
            }
            binding.radioButtonAutocross.id ->{
                adapterPracticeSession.filterListByEventChecked(query, "Autocross")
            }
            binding.radioButtonSkidpad.id->{
                adapterPracticeSession.filterListByEventChecked(query, "Skidpad")
            }
        }
    }

}