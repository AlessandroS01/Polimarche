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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralPracticeSessionSeePracticeSessionBinding
import com.example.polimarche.data_container.practice_session.PracticeSessionViewModel

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
            /*
            Whenever the length of the query is 10 and the checkBox change,
             it will show the items that has date equals to the query given.
            */
            if(searchView.query.length == 10){
                setQueryFilterByEventCategory(searchView.query.toString())
            }
        }
        /*
        Changes the query hint on the change of the checked radioButton
         */
        radioGroupEventCategories.setOnCheckedChangeListener { _, checkedId ->
            searchView.queryHint = setQueryHintSearchView(checkedId)
            /*
            Whenever the length of the query is 10 and the radio button
            checked change, it will show the items that has as event the same
            one given as input trough the radio buttons during the date
            given as query.
            */
            if(searchView.query.length == 10){
                setQueryFilterByEventCategory(searchView.query.toString())
            }
        }

        recyclerViewSeePracticeSession = binding.listPracticeSession
        val linearLayoutManager = LinearLayoutManager(this.requireContext())
        recyclerViewSeePracticeSession.layoutManager = linearLayoutManager
        adapterPracticeSession = SeePracticeSessionAdapter(practiceSessionViewModel)
        recyclerViewSeePracticeSession.adapter = adapterPracticeSession


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                /*
                Blocks the submit whenever the query contains at least one letter and the
                length is smaller than 10.
                 */
                return if(query != null && query.none{ it.isLetter() } && query.length == 10){
                    searchView.clearFocus()
                    setQueryFilterByEventCategory(query)
                    true
                } else false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                /*
                It restores the default list.
                 */
                adapterPracticeSession.restoreListToDefault()
                if (newText != null) {
                    /*
                    Prevent the query to have a length higher than 10.
                     */
                    if(newText.length>10){
                        searchView.setQuery(newText.substring(0, 10), false)
                    }
                    /*
                    Sets the query color to red whenever a letter is contained inside the query.
                    It also add the character "-" to the query when the length is 4 or 7 to divide
                    automatically the date.
                     */
                    if(newText.none {
                            it.isLetter()
                        }
                    ){
                        searchView.findViewById<EditText>(
                            androidx.appcompat.R.id.search_src_text
                        ).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        if(newText.length == 4 || newText.length == 7){
                            searchView.setQuery("$newText-", false)
                        }
                        return true
                    }
                    else {
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

    /*
    Method that checks if the checkBox is checked and, in case,
    which radio button is selected.
    Returns 0 when the checkBox is not checked and the radioButton id
    when the checkbox and the radioButton is selected.
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
    Checks if the checkbox and the radio button are clicked.
    Basing on the return of the method, the recyclerView will show
    only certain items that respects some filters.
    */
    private fun setQueryFilterByEventCategory(query: String){
        when(findRadioButtonChecked()){
            0->{
                adapterPracticeSession.filterListWithoutEvents(query)
            }
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