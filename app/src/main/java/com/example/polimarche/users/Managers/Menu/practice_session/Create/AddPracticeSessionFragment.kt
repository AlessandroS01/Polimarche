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
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersPracticeSessionAddBinding
import com.example.polimarche.data_container.practice_session.DataPracticeSession
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.practice_session.PracticeSessionViewModel
import com.example.polimarche.data_container.track.TracksViewModel
import java.time.LocalDate
import java.time.LocalTime

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
    These variables controls if the values inserted as date and time are correct or not.
     */
    private var correctnessDate = false
    private var correctnessStartingTime = false
    private var correctnessEndingTime = false
    // Variable in which we will store the name of the track selected by the user
    private var trackName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersPracticeSessionAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSeeTracks = binding.listTracksAddPS
        val linearLayoutManager = LinearLayoutManager(requireContext())
        adapterSeeTracks = UseTrackNewPracticeSessionAdapter(tracksViewModel, this)
        recyclerViewSeeTracks.layoutManager = linearLayoutManager
        recyclerViewSeeTracks.adapter = adapterSeeTracks

        setDate()
        setTime()

        binding.linearLayoutCreatePS.setOnClickListener {
            /*
            Checks if the date, the time and all the other edit text are inserted
            and controls the correctness of date and time.
            If everything is correct then a new occurrence of DataPracticeSession will be created
            and it will be passed to the repository trough the viewModel.
            Then every input text will be cleared.
             */
            if(trackName != ""
                && correctnessDate
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
                Restore the initial state of track input and event type
                 */
                binding.setTrackPS.text = "Set track"
                adapterSeeTracks.changeListTracksOnClick(tracksViewModel.listTracks)
                binding.radioGroupEventCategories.check(binding.radioButtonEndurance.id)

                // Create a list of editText elements inside the view
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
                Clear the text inside all the editTexts
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
        Generates a new list containing all the tracks that are different from the one selected
         */
        val newListTracks: MutableLiveData<MutableList<DataTrack>> =
            MutableLiveData(
                tracksViewModel.listTracks.value?.filter {
                    it != trackClicked
                }?.toMutableList()!!
            )
        /*
        Changes the shown list of the recyclerView.
         */
        adapterSeeTracks.changeListTracksOnClick(newListTracks)
    }

    /*
    Used to control the input of the date
     */
    private fun setDate(){
        /*
        When the user clicks on the button, the edit text will contain
        the current date.
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
        // Used to keep a record of the length of the text
        var previousLength = 0
        binding.setDatePSEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.length == 10){
                    val year = s.subSequence(0, 4)
                    val month = s.subSequence(5, 7)
                    val day = s.subSequence(8, 10)

                    /*
                    Sets the text to red when the date inserted is not valid.
                     */
                    if (year.toString().toInt() != LocalDate.now().year ||
                        month.toString().toInt() > 12 || month.toString().toInt() == 0 ||
                        day.toString().toInt() > 31 || day.toString().toInt() == 0  ) {
                        binding.setDatePSEditText.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                        correctnessDate = false
                    }
                    else correctnessDate = true
                }
            }
            override fun afterTextChanged(s: Editable?) {
                // Sees the actual length of the string
                val currentLength = s?.length ?: 0
                /*
                Calculate the difference between the current length of the string and
                the previous length.
                So whenever the user deletes a character the difference will be signed
                negatively.
                 */
                val diff = currentLength - previousLength
                /*
                If the difference is positive, so the user is writing and not deleting characters,
                whenever the string length is 4 or 7, to the editText text will be added "-".
                Then, since setText set focus to the first character, we need to set the
                focus at the end of the string.
                 */
                if (diff > 0 && (currentLength == 4 || currentLength == 7)) {
                    binding.setDatePSEditText.setText("$s-")
                    binding.setDatePSEditText.setSelection(binding.setDatePSEditText.text.length)
                }
                previousLength = currentLength // update previous length
                if(currentLength != 10)
                    binding.setDatePSEditText.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white)
                    )

            }
        })
    }

    /*
    Used to control the input of the time
     */
    private fun setTime(){

        // Used to keep a record of the length of the text
        var previousLength = 0
        binding.setStartingTimePS.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Checks if the time inserted is valid or not.
                 */
                if(s?.length == 8){
                    val hours = s.subSequence(0, 2)
                    val minutes = s.subSequence(3, 5)
                    val seconds = s.subSequence(6, 8)

                    /*
                    Sets the text to red when the time inserted is not valid.
                     */
                    if (hours.toString().toInt() > 24 ||
                        minutes.toString().toInt() > 60 ||
                        seconds.toString().toInt() > 60 ) {
                            binding.setStartingTimePS.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                            correctnessStartingTime = false
                    }
                    else correctnessStartingTime = true
                    controlOverTimeSubmitted(correctnessStartingTime, correctnessEndingTime)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Sees the actual length of the string
                val currentLength = s?.length ?: 0
                /*
                Calculate the difference between the current length of the string and
                the previous length.
                So whenever the user deletes a character the difference will be signed
                negatively.
                 */
                val diff = currentLength - previousLength
                if(diff > 0 && ( currentLength == 2 || currentLength == 5) ){
                    binding.setStartingTimePS.setText("$s:")
                    binding.setStartingTimePS.setSelection(binding.setStartingTimePS.text.length)
                }
                previousLength = currentLength // update previous length
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
                    val hours = s.subSequence(0, 2)
                    val minutes = s.subSequence(3, 5)
                    val seconds = s.subSequence(6, 8)

                    /*
                    Sets the text to red when the time inserted is not valid.
                     */
                    if (hours.toString().toInt() > 24 ||
                        minutes.toString().toInt() > 60 ||
                        seconds.toString().toInt() > 60 ) {
                            binding.setEndingTimePS.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                            correctnessEndingTime = false
                    }
                    else correctnessEndingTime = true
                    controlOverTimeSubmitted(correctnessStartingTime, correctnessEndingTime)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Sees the actual length of the string
                val currentLength = s?.length ?: 0
                /*
                Calculate the difference between the current length of the string and
                the previous length.
                So whenever the user deletes a character the difference will be signed
                negatively.
                 */
                val diff = currentLength - previousLength2
                if(diff > 0 && ( currentLength == 2 || currentLength == 5) ){
                    binding.setEndingTimePS.setText("$s:")
                    binding.setEndingTimePS.setSelection(binding.setEndingTimePS.text.length)
                }
                previousLength2 = currentLength // update previous length
                if(currentLength != 8)
                    binding.setEndingTimePS.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white)
                    )
            }

        })

    }
    /*
    Parse the string created as input by the user and controls if the starting time
    is before the ending time.
    If that's not happening then the color of setTime will be set to red.
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
            }
            else binding.setTimePS.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
        }
    }

    /*
    Returns a string that has as value the name of the event
    linked to the radioButton checked.
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