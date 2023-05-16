package com.example.polimarche.users.managers.menu.setup.create.choosing_balance

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersExistingBalanceParametersBinding
import com.example.polimarche.data_container.balance.BalanceViewModel
import com.example.polimarche.data_container.balance.DataBalance

class ExistingBalanceParameters(
    private val view1: ImageView?, private val view2: ImageView?
 ): Fragment(R.layout.fragment_managers_existing_balance_parameters) {

    private var _binding: FragmentManagersExistingBalanceParametersBinding? = null
    private val binding get() = _binding!!

    private val balanceViewModel: BalanceViewModel by viewModels()

    private var parametersStocked: MutableLiveData<DataBalance>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersExistingBalanceParametersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewVisibility(binding.inputBalanceCode)

        // Checks if the new set of parameters are for front or back end.
        when(arguments?.getString("BALANCE_POSITION")){
            "Front"-> {
                // Checks if a set of parameters is already stored.
                if( balanceViewModel.getFrontBalanceParameters() != null ){
                    parametersStocked = balanceViewModel.getFrontBalanceParameters()
                    // If the code of the parameters stocked is the same as the code of one
                    // element's of the list of different balance parameters, then it sets the
                    // text value inside the edit text equals to the code of the parameters stocked.
                    if (
                        balanceViewModel.balanceList.value?.none{
                            it.code == parametersStocked?.value?.code!!
                                    &&
                            it.end == "Front"
                        }!!
                    )
                    {
                        binding.inputBalanceCode.setText(
                            parametersStocked?.value?.code.toString()
                        )
                    }
                }
            }
            else -> {
                if( balanceViewModel.getBackBalanceParameters() != null ){
                    parametersStocked = balanceViewModel.getBackBalanceParameters()!!
                    if (
                        balanceViewModel.balanceList.value?.filter{
                            it.code == parametersStocked?.value?.code!!
                                    &&
                            it.end == "Back"
                        }?.size != 0
                    )
                    {
                        binding.inputBalanceCode.setText(
                            parametersStocked?.value?.code.toString()
                        )
                    }
                }
            }
        }

        binding.inputBalanceCode.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() != "") {
                    when (arguments?.getString("BALANCE_POSITION")) {
                        "Front" -> {
                            /*
                            Changes the color of the edit text to red when the code given doesn't
                            match any others code of the entire list of parameters and to white
                            when it matches.
                             */
                            if (
                                balanceViewModel.getFrontBalanceList().none {
                                    it.code == s.toString().toInt()
                                }
                            ) {
                                binding.inputBalanceCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red_700
                                    )
                                )
                            } else {
                                binding.inputBalanceCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                /*
                                Sets the value of the front balance parameters used to create a new
                                setup as the same of the set of parameters that has the code equals
                                to the one given by the user as input.
                                 */
                                balanceViewModel.setFrontBalanceParameters(
                                    balanceViewModel.getFrontBalanceList().filter {
                                        it.code == s.toString().toInt()
                                    }[0]
                                )
                            }
                        }
                        else -> {
                            if (
                                balanceViewModel.getBackBalanceList().none {
                                    it.code == s.toString().toInt()
                                }
                            ) {
                                binding.inputBalanceCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red_700
                                    )
                                )
                            } else {
                                binding.inputBalanceCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                balanceViewModel.setBackBalanceParameters(
                                    balanceViewModel.getBackBalanceList().filter {
                                        it.code == s.toString().toInt()
                                    }[0]
                                )
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    /*
    Set the visibility of the 2 image Views used to navigate trough the different
    fragments to NONE when the editText is focused and to VISIBLE when it's not focused.

    Then it sets the visibility of the 2 image Views used to navigate trough the different
    fragments to VISIBLE after the submit of the input inside the editText.
    Furthermore it hides the keyboard at the same time.
     */
    private fun setViewVisibility(editText: EditText) {

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (view1 != null) view1.visibility = View.GONE
                if (view2 != null) view2.visibility = View.GONE
            } else {
                view1?.visibility = View.VISIBLE
                view2?.visibility = View.VISIBLE
            }
        }

        editText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                view1?.visibility = View.VISIBLE
                view2?.visibility = View.VISIBLE
                v.clearFocus()
            }
            true
        }
    }


}
