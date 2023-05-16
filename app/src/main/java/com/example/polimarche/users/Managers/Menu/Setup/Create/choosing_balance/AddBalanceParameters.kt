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
import com.example.mobileprogramming.databinding.FragmentManagersAddBalanceParametersBinding
import com.example.polimarche.data_container.balance.BalanceViewModel
import com.example.polimarche.data_container.balance.DataBalance

class AddBalanceParameters(
    private val view1: ImageView?, private val view2: ImageView?
): Fragment(R.layout.fragment_managers_add_balance_parameters) {

    private var _binding: FragmentManagersAddBalanceParametersBinding? = null
    private val binding get() = _binding!!

    private val balanceViewModel: BalanceViewModel by viewModels()

    private var parametersStocked: MutableLiveData<DataBalance>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersAddBalanceParametersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewVisibility(binding.inputBalanceCode)
        setViewVisibility(binding.inputBrakeBalance)
        setViewVisibility(binding.inputWeightBalance)

        // Checks if the new set of parameters are for front or back end.
        // If already exists then it sets the text value of the 3 different edit text.
        when(arguments?.getString("BALANCE_POSITION")){
            "Front"-> {
                /*
                 Sets the value of parameterStocked if inside the repository there is already
                 a set of parameter set.
                 */
                if( balanceViewModel.getFrontBalanceParametersStocked() != null ){
                    parametersStocked = balanceViewModel.getFrontBalanceParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        balanceViewModel.balanceList.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedBalanceParameters(parametersStocked!!)
                    }
                }
            }
            else -> {
                if( balanceViewModel.getBackBalanceParametersStocked() != null ){
                    parametersStocked = balanceViewModel.getBackBalanceParametersStocked()!!
                    if(
                        balanceViewModel.balanceList.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ) {
                        setStockedBalanceParameters(parametersStocked!!)
                    }
                }
            }
        }

        binding.inputBrakeBalance.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputBalanceCode.text.isNotEmpty()) {
                    val balanceCode: Int = binding.inputBalanceCode.text.toString().toInt()
                    if (
                        balanceViewModel.balanceList.value?.any {
                            it.code == balanceCode
                        }!!
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
                    }
                }
                /*
                Checks if the value of weight is given.
                If it's given and the value of brake is different from an empty string,
                firstly it analyzes if the balance code is given.
                 */
                if(
                    binding.inputWeightBalance.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputBalanceCode.text.isNotEmpty() ){
                        val balanceCode: Int = binding.inputBalanceCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            balanceViewModel.balanceList.value?.any {
                                it.code == balanceCode
                            }!!
                        ){
                            binding.inputBalanceCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                        }
                        /*
                        When the code is not used then it can create and set the new set of
                        balance parameters.
                         */
                        else {
                            binding.inputBalanceCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setBalanceIntoRepository(balanceCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var balanceCode = 1
                        while(
                            balanceViewModel.balanceList.value?.any {
                                it.code == balanceCode
                            }!!
                        ){
                            balanceCode++
                        }
                        setBalanceIntoRepository(balanceCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        // The operations of the 2 following parts are similar to the previous one.
        binding.inputWeightBalance.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if( binding.inputBalanceCode.text.isNotEmpty() ) {

                        val balanceCode: Int = binding.inputBalanceCode.text.toString().toInt()
                        if (
                            balanceViewModel.balanceList.value?.any {
                                it.code == balanceCode
                            }!!
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
                        }
                    }
                    if(
                        binding.inputBrakeBalance.text.isNotEmpty()
                        &&
                        s.toString() != ""
                    ){
                        if( binding.inputBalanceCode.text.isNotEmpty() ){
                            val balanceCode: Int = binding.inputBalanceCode.text.toString().toInt()
                            if(
                                balanceViewModel.balanceList.value?.any {
                                    it.code == balanceCode
                                }!!
                            ){
                                binding.inputBalanceCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red_700
                                    )
                                )
                            }
                            else {
                                binding.inputBalanceCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                setBalanceIntoRepository(balanceCode)
                            }
                        }
                        else{
                            var balanceCode = 1
                            while(
                                balanceViewModel.balanceList.value?.any {
                                    it.code == balanceCode
                                }!!
                            ){
                                balanceCode++
                            }
                            setBalanceIntoRepository(balanceCode)
                        }
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        binding.inputBalanceCode.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(
                        s.toString() != ""
                        &&
                        balanceViewModel.balanceList.value?.any {
                            it.code == s.toString().toInt()
                        }!!
                    ) {
                        binding.inputBalanceCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    }
                    else{
                        binding.inputBalanceCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        if(
                            binding.inputBrakeBalance.text.isNotEmpty()
                            &&
                            binding.inputWeightBalance.text.isNotEmpty()
                            &&
                            s.toString() != ""
                        ){
                            val balanceCode = s.toString().toInt()
                            setBalanceIntoRepository(balanceCode)
                        }
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })


    }

    /*
    Set the visibility of the 2 image Views used to navigate trough the different
    fragments to NONE when the editText is focused and to VISIBLE when it's not focused.

    Then it sets the visibility of the 2 image Views used to navigate trough the different
    fragments to VISIBLE after the submit of the input inside the editText.
    Furthermore it hides the keyboard at the same time.
     */
    private fun setViewVisibility(editText: EditText){

        editText.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                if (view1 != null) view1.visibility = View.GONE
                if (view2 != null) view2.visibility = View.GONE
            }
            else{
                view1?.visibility = View.VISIBLE
                view2?.visibility = View.VISIBLE
            }
        }

        editText.setOnEditorActionListener { v, actionId, _ ->
             if (actionId == EditorInfo.IME_ACTION_DONE) {
                 val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                 imm.hideSoftInputFromWindow(v.windowToken, 0)
                 view1?.visibility = View.VISIBLE
                 view2?.visibility = View.VISIBLE
                 v.clearFocus()
             }
             true
        }
    }

    private fun setStockedBalanceParameters(dataBalance: MutableLiveData<DataBalance>){
        binding.inputBalanceCode.setText(dataBalance.value?.code.toString())
        binding.inputBrakeBalance.setText(dataBalance.value?.brake.toString())
        binding.inputWeightBalance.setText(dataBalance.value?.weight.toString())
    }

    private fun setBalanceIntoRepository(balanceCode: Int){
        when(arguments?.getString("BALANCE_POSITION")){
            "Front" -> {
                val newBalance = DataBalance(
                    balanceCode,
                    "Front",
                    binding.inputBrakeBalance.text.toString().toDouble(),
                    binding.inputWeightBalance.text.toString().toDouble()
                )
                balanceViewModel.setFrontBalanceParametersStocked(newBalance)
            }
            else -> {
                val newBalance = DataBalance(
                    balanceCode,
                    "Back",
                    binding.inputBrakeBalance.text.toString().toDouble(),
                    binding.inputWeightBalance.text.toString().toDouble()
                )
                balanceViewModel.setBackBalanceParametersStocked(newBalance)
            }
        }
    }
}