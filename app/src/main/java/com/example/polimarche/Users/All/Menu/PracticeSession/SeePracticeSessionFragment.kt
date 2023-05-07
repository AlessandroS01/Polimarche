package com.example.polimarche.Users.All.Menu.PracticeSession

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralPracticeSessionSeePracticeSessionBinding

class SeePracticeSessionFragment : Fragment(
    R.layout.fragment_general_practice_session_see_practice_session
){
    private var _binding: FragmentGeneralPracticeSessionSeePracticeSessionBinding? = null
    private val binding get() = _binding!!

    private val practiceSessionViewModel: PracticeSessionViewModel by viewModels()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchForEventCategoryCheckBox = binding.checkBoxEventCategories
        val radioGroupEventCategories = binding.radioGroupEventCategories
        radioGroupEventCategories.visibility = View.GONE

        searchView = binding.searchViewPracticeSession
        /*
        Used to set the query hint to "Practice session date \"YYYY-MM-DD\""
         */
        searchView.queryHint = setQueryHintSearchView(0)


        /*
        Sets the visibility of the radio group to NONE whenever the checked box is unchecked.
        It also sets the query hint of the searchView.
         */
        searchForEventCategoryCheckBox.setOnCheckedChangeListener { _, isChecked ->
            radioGroupEventCategories.visibility = if (isChecked) View.VISIBLE else View.GONE
            searchView.queryHint = if (!isChecked) "Practice session date \"YYYY-MM-DD\"" else {
                setQueryHintSearchView(radioGroupEventCategories.checkedRadioButtonId)
            }
        }

        /*
        Changes the query hint on the change of the checked radioButton
         */
        radioGroupEventCategories.setOnCheckedChangeListener { _, checkedId ->
            searchView.queryHint = setQueryHintSearchView(checkedId)
        }


        recyclerViewSeePracticeSession = binding.listPracticeSession
        val linearLayoutManager = LinearLayoutManager(this.requireContext())
        recyclerViewSeePracticeSession.layoutManager = linearLayoutManager
        adapterPracticeSession = SeePracticeSessionAdapter(practiceSessionViewModel)
        recyclerViewSeePracticeSession.adapter = adapterPracticeSession

    }

    /*
    Returns the query hint inside the search view based on the checked radioButton.
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

}