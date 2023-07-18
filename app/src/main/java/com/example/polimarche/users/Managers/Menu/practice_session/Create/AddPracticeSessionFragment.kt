package com.example.polimarche.users.managers.menu.practice_session.Create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentManagersPracticeSessionAddBinding
import com.example.polimarche.data_container.practice_session.DataPracticeSession
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.practice_session.PracticeSessionViewModel
import com.example.polimarche.data_container.track.TracksViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AddPracticeSessionFragment : Fragment(
    R.layout.fragment_managers_practice_session_add
), UseTrackNewPracticeSessionAdapter.OnTrackClickListener{

    private var _binding: FragmentManagersPracticeSessionAddBinding? = null
    private val binding get() = _binding!!

    private val tracksViewModel: TracksViewModel by viewModels()

    private val practiceSessionViewModel: PracticeSessionViewModel by viewModels()

    private lateinit var recyclerViewSeeTracks: RecyclerView
    private lateinit var adapterSeeTracks: UseTrackNewPracticeSessionAdapter

    /*
    Queste variabili controllano se i valori inseriti nella date e negli orari siano corretti o meno
     */
    private var correctnessDate = false
    private var correctnessTime = false
    private var correctnessStartingTime = false
    private var correctnessEndingTime = false
    // Variabile nella quale andremo a salvare il nome della track selezionata dall'utente
    private var trackName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // La funzione inflate prende il layout XML del fragment_general_practice_session_add
        // e lo converte in un oggetto FragmentPracticeSessionAddBinding
        _binding = FragmentManagersPracticeSessionAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tracksViewModel.initialize()
        // Impostazione un observer sull'oggetto LiveData listPracticeSession
        // all'interno del practiceSessionViewModel.
        // L'observer viene notificato ogni volta che il valore di listPracticeSession cambia.
        tracksViewModel.listTracks.observe(viewLifecycleOwner) {
            recyclerViewSeeTracks = binding.listTracksAddPS
            val linearLayoutManager = LinearLayoutManager(requireContext())
            adapterSeeTracks = UseTrackNewPracticeSessionAdapter(tracksViewModel, this)
            recyclerViewSeeTracks.layoutManager = linearLayoutManager
            recyclerViewSeeTracks.adapter = adapterSeeTracks
        }
        setDate()
        setTime()

        binding.linearLayoutCreatePS.setOnClickListener {
            /*
            Controlla se la data, l'ora e tutti gli altri testi di modifica sono inseriti
            e controlla la correttezza di data e ora.
            Se tutto è corretto, verrà creata una nuova occorrenza di DataPracticeSession
            e verrà passato al repository tramite viewModel.
            Quindi ogni testo di input verrà cancellato.
             */
            if(trackName != ""
                && correctnessDate
                && correctnessTime
                && correctnessStartingTime
                && correctnessEndingTime
                && binding.setWeatherEditTextPS.text.isNotEmpty()
                && binding.setTrackConditionEditTextPS.text.isNotEmpty()
                && binding.setTrackTemperatureEditTextPS.text.isNotEmpty()
                && binding.setAmbientPressureEditTextPS.text.isNotEmpty()
                && binding.setAirTemperatureEditTextPS.text.isNotEmpty()
            ){
                val newPracticeSession: DataPracticeSession =
                    DataPracticeSession(
                        findRadioButtonChecked(),
                        LocalDate.parse(binding.setDatePSEditText.text),
                        LocalTime.parse(binding.setStartingTimePS.text),
                        LocalTime.parse(binding.setEndingTimePS.text),
                        trackName,
                        binding.setWeatherEditTextPS.text.toString(),
                        binding.setTrackConditionEditTextPS.text.toString(),
                        binding.setTrackTemperatureEditTextPS.text.toString().toDouble(),
                        binding.setAmbientPressureEditTextPS.text.toString().toDouble(),
                        binding.setAirTemperatureEditTextPS.text.toString().toDouble()
                    )

                practiceSessionViewModel.addNewPracticeSession(newPracticeSession)

                Toast.makeText(
                    requireContext(),
                    "Practice session added",
                    Toast.LENGTH_LONG
                ).show()

                /*
                Ristabilisce lo stato iniziale del track inout e dell'event type
                 */
                binding.setTrackPS.text = "Set track"
                adapterSeeTracks.changeListTracksOnClick(tracksViewModel.listTracks)
                binding.radioGroupEventCategories.check(binding.radioButtonEndurance.id)

                // Crea una lista di elementi dentro la View
                val editTexts = listOf(
                    binding.setDatePSEditText,
                    binding.setStartingTimePS,
                    binding.setEndingTimePS,
                    binding.setWeatherEditTextPS,
                    binding.setTrackConditionEditTextPS,
                    binding.setTrackTemperatureEditTextPS,
                    binding.setAmbientPressureEditTextPS,
                    binding.setAirTemperatureEditTextPS
                )

                /*
                Pulisce il testo dentro tutti gli editTexts
                 */
                editTexts.forEach {
                    it.text.clear()
                }

            }

        }
    }

    /*
    Sets the track on click on an item inside the recyclerview.
     */
    override fun onTrackClickListener(trackClicked: DataTrack) {
        binding.setTrackPS.text = "Track choosen: ${trackClicked.name}"
        trackName = trackClicked.name
        /*
        Genera una nuova lista contenente tutte le tracks differenti da quella selezionata
         */
        val newListTracks: MutableLiveData<MutableList<DataTrack>> =
            MutableLiveData(
                tracksViewModel.listTracks.value?.filter {
                    it != trackClicked
                }?.toMutableList()!!
            )
        /*
        Cambia la visualizzazione della lista della RecyclerView.
         */
        adapterSeeTracks.changeListTracksOnClick(newListTracks)
    }

    /*
    Utilizzato per controllare l'input della data.
     */
    private fun setDate(){
        /*
        Quando l'utente clicca sul bottone, l'edit text conterrà la data corrente.
         */
        binding.setDatePSButton.setOnClickListener {
            binding.setDatePSEditText.setText(
                LocalDate.now().toString()
            )
            binding.setDatePSEditText.clearFocus()
            binding.setDatePSEditText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
        //Utilizzato per mantenere un record della lunghezza del testo
        var previousLength = 0
        binding.setDatePSEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.length == 10){
                    /*
                    Setta il testo a rosso quando la data inserita non è valida.
                     */
                    correctnessDate = if (isValidDate(s.toString(), "yyyy-MM-dd")) {
                        true
                    } else {

                        binding.setDatePSEditText.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                        false
                    }

                }
            }
            override fun afterTextChanged(s: Editable?) {
                // Sees the actual length of the string
                val currentLength = s?.length ?: 0
                /*
                Calcola la differenza tra la current lenght della stringa e la previous length.
                Quindi in qualsiasi momento l'utente elimina un carattere, la variabile diff
                sarà negativa.
                 */
                val diff = currentLength - previousLength
                /*
                Se la differenza è positiva, quindi l'utente sta scrivendo e non eliminando
                caratteri, quando la lunghezza della stringa è 4 o 7, verrà aggiunto un "-"
                all'ediText. Poi, poiché setText setta il focus sul primo carattere, dobbiamo
                settare il focus alla fine della stringa.
                 */
                if (diff > 0 && (currentLength == 4 || currentLength == 7)) {
                    binding.setDatePSEditText.setText("$s-")
                    binding.setDatePSEditText.setSelection(binding.setDatePSEditText.text.length)
                }
                previousLength = currentLength //Aggiorna la lunghezza precedente
                if(currentLength != 10)
                    binding.setDatePSEditText.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white)
                    )

            }
        })
    }

    //Se l'analisi ha successo e non viene generata alcuna eccezione,
    // la funzione restituisce true, indicando che dateStr è una data valida secondo
    // il formato specificato. Se si verifica un'eccezione durante l'analisi,
    // la funzione cattura l'eccezione e restituisce false,
    // indicando che dateStr non è una data valida.
    fun isValidDate(dateStr: String, format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.isLenient = false
        return try {
            sdf.parse(dateStr)
            true
        } catch (e: Exception) {
            false
        }
    }

    /*
    Utilizzato per controllare l'input dell'orario
    Used to control the input of the time
     */
    private fun setTime(){

        //Utilizzato per mantenere un record della lunghezza dell'orario
        var previousLength = 0
        binding.setStartingTimePS.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Controlla se l'orario inserito è valido o meno.
                 */
                if(s?.length == 8){
                    /*
                    Setta il testo a rosso quando l'orario inserito non è corretto.
                     */
                    if (isValidTime(s.toString(), "HH:mm:ss")) {
                        correctnessStartingTime = true
                        controlOverTimeSubmitted(correctnessStartingTime, correctnessEndingTime)
                    } else {
                        binding.setStartingTimePS.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                        correctnessStartingTime = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Vede la lunghezza attuale della stringa
                val currentLength = s?.length ?: 0
                /*
                Calcola la differenza tra la current lenght della stringa e la previous length.
                Quindi in qualsiasi momento l'utente elimina un carattere, la variabile diff
                sarà negativa.
                 */
                val diff = currentLength - previousLength
                if(diff > 0 && ( currentLength == 2 || currentLength == 5) ){
                    binding.setStartingTimePS.setText("$s:")
                    binding.setStartingTimePS.setSelection(binding.setStartingTimePS.text.length)
                }
                previousLength = currentLength // Aggiorna la lunghezza precedente
                if(currentLength != 8)
                    binding.setStartingTimePS.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white)
                    )
            }

        })

        var previousLength2 = 0
        binding.setEndingTimePS.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.length == 8){
                    /*
                    Setta il testo a rosso quando l'orario inserito non è corretto
                     */
                    if (isValidTime(s.toString(), "HH:mm:ss")) {
                        correctnessEndingTime = true
                        controlOverTimeSubmitted(correctnessStartingTime, correctnessEndingTime)
                    } else {
                        binding.setStartingTimePS.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                        correctnessEndingTime = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Vede la lunghezza attuale della stringa
                val currentLength = s?.length ?: 0
                /*
                Calcola la differenza tra la current lenght della stringa e la previous length.
                Quindi in qualsiasi momento l'utente elimina un carattere, la variabile diff
                sarà negativa.
                 */
                val diff = currentLength - previousLength2
                if(diff > 0 && ( currentLength == 2 || currentLength == 5) ){
                    binding.setEndingTimePS.setText("$s:")
                    binding.setEndingTimePS.setSelection(binding.setEndingTimePS.text.length)
                }
                previousLength2 = currentLength // Aggiorna la lunghezza precedente
                if(currentLength != 8)
                    binding.setEndingTimePS.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white)
                    )
            }

        })

    }

    fun isValidTime(timeStr: String, format: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern(format)
            LocalTime.parse(timeStr, formatter)
            true
        } catch (e: Exception) {
            false
        }
    }
    /*
    Effettua il parsing della stringa creata in input dall'utente e controlla se l'orario di inizio
    sia precedente a quello di fine.à
    Se ciò non accade allora il colore del setTime verrà settato a rosso.
     */
    private fun controlOverTimeSubmitted(
        startingTimeCorrectness: Boolean,
        endingTimeCorrectness: Boolean
    ){
        if (startingTimeCorrectness && endingTimeCorrectness){
            val startingTime = LocalTime.parse(binding.setStartingTimePS.text)
            val endingTime = LocalTime.parse(binding.setEndingTimePS.text)
            if(startingTime.isAfter(endingTime) || startingTime.equals(endingTime)){
                binding.setTimePS.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red_700
                    )
                )
                correctnessTime = false
            }
            else {
                binding.setTimePS.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                correctnessTime = true
            }
        }
    }

    /*
    Ritorna una stringa che ha come valore il nome dell'evento
    collgeato al radio button checkato
     */
    private fun findRadioButtonChecked(): String{
        when(binding.radioGroupEventCategories.checkedRadioButtonId){
            binding.radioButtonAcceleration.id -> return "Acceleration"
            binding.radioButtonAutocross.id -> return "Autocross"
            binding.radioButtonEndurance.id -> return "Endurance"
            binding.radioButtonSkidpad.id -> return "Skidpad"
        }
        return "Error"
    }


}