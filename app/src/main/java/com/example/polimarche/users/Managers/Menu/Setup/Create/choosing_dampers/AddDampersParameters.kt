package com.example.polimarche.users.managers.menu.setup.create.choosing_dampers

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
import com.example.mobileprogramming.databinding.FragmentManagersAddDampersParametersBinding
import com.example.polimarche.data_container.damper.DamperViewModel
import com.example.polimarche.data_container.damper.DataDamper

class AddDampersParameters(
    private val view1: ImageView?, private val view2: ImageView?
): Fragment(R.layout.fragment_managers_add_dampers_parameters) {

    private var _binding: FragmentManagersAddDampersParametersBinding? = null
    private val binding get() = _binding!!

    private val damperViewModel: DamperViewModel by viewModels()

    private var parametersStocked: MutableLiveData<DataDamper>? = null




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersAddDampersParametersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewVisibility(binding.inputDamperCode)
        setViewVisibility(binding.inputDamperHsr)
        setViewVisibility(binding.inputDamperHsc)
        setViewVisibility(binding.inputDamperLsr)
        setViewVisibility(binding.inputDamperLsc)

        // Checks if the new set of parameters are for front or back end.
        // If already exists then it sets the text value of the 3 different edit text.
        when(arguments?.getString("DAMPER_POSITION")){
            "Front"-> {
                /*
                 Sets the value of parameterStocked if inside the repository there is already
                 a set of parameter set.
                 */
                if( damperViewModel.getFrontDamperParametersStocked() != null ){
                    parametersStocked = damperViewModel.getFrontDamperParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        damperViewModel.listDampers.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedDamperParameters(parametersStocked!!)
                    }
                }
            }
            else -> {
                if( damperViewModel.getBackDamperParametersStocked() != null ){
                    parametersStocked = damperViewModel.getBackDamperParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        damperViewModel.listDampers.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedDamperParameters(parametersStocked!!)
                    }
                }
            }
        }


        binding.inputDamperHsr.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputDamperCode.text.isNotEmpty()) {
                    val damperCode: Int = binding.inputDamperCode.text.toString().toInt()
                    if (
                        damperViewModel.listDampers.value?.any {
                            it.code == damperCode
                        }!!
                    ) {
                        binding.inputDamperCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    } else {
                        binding.inputDamperCode.setTextColor(
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
                    binding.inputDamperHsc.text.isNotEmpty()
                    &&
                    binding.inputDamperLsr.text.isNotEmpty()
                    &&
                    binding.inputDamperLsc.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputDamperCode.text.isNotEmpty() ){
                        val damperCode: Int = binding.inputDamperCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            damperViewModel.listDampers.value?.any {
                                it.code == damperCode
                            }!!
                        ){
                            binding.inputDamperCode.setTextColor(
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
                            binding.inputDamperCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setDamperIntoRepository(damperCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var damperCode = 1
                        while(
                            damperViewModel.listDampers.value?.any {
                                it.code == damperCode
                            }!!
                        ){
                            damperCode++
                        }
                        setDamperIntoRepository(damperCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.inputDamperHsc.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputDamperCode.text.isNotEmpty()) {
                    val damperCode: Int = binding.inputDamperCode.text.toString().toInt()
                    if (
                        damperViewModel.listDampers.value?.any {
                            it.code == damperCode
                        }!!
                    ) {
                        binding.inputDamperCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    } else {
                        binding.inputDamperCode.setTextColor(
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
                    binding.inputDamperHsr.text.isNotEmpty()
                    &&
                    binding.inputDamperLsr.text.isNotEmpty()
                    &&
                    binding.inputDamperLsc.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputDamperCode.text.isNotEmpty() ){
                        val damperCode: Int = binding.inputDamperCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            damperViewModel.listDampers.value?.any {
                                it.code == damperCode
                            }!!
                        ){
                            binding.inputDamperCode.setTextColor(
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
                            binding.inputDamperCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setDamperIntoRepository(damperCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var damperCode = 1
                        while(
                            damperViewModel.listDampers.value?.any {
                                it.code == damperCode
                            }!!
                        ){
                            damperCode++
                        }
                        setDamperIntoRepository(damperCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.inputDamperLsr.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputDamperCode.text.isNotEmpty()) {
                    val damperCode: Int = binding.inputDamperCode.text.toString().toInt()
                    if (
                        damperViewModel.listDampers.value?.any {
                            it.code == damperCode
                        }!!
                    ) {
                        binding.inputDamperCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    } else {
                        binding.inputDamperCode.setTextColor(
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
                    binding.inputDamperHsr.text.isNotEmpty()
                    &&
                    binding.inputDamperHsc.text.isNotEmpty()
                    &&
                    binding.inputDamperLsc.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputDamperCode.text.isNotEmpty() ){
                        val damperCode: Int = binding.inputDamperCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            damperViewModel.listDampers.value?.any {
                                it.code == damperCode
                            }!!
                        ){
                            binding.inputDamperCode.setTextColor(
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
                            binding.inputDamperCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setDamperIntoRepository(damperCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var damperCode = 1
                        while(
                            damperViewModel.listDampers.value?.any {
                                it.code == damperCode
                            }!!
                        ){
                            damperCode++
                        }
                        setDamperIntoRepository(damperCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.inputDamperLsc.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputDamperCode.text.isNotEmpty()) {
                    val damperCode: Int = binding.inputDamperCode.text.toString().toInt()
                    if (
                        damperViewModel.listDampers.value?.any {
                            it.code == damperCode
                        }!!
                    ) {
                        binding.inputDamperCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    } else {
                        binding.inputDamperCode.setTextColor(
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
                    binding.inputDamperHsr.text.isNotEmpty()
                    &&
                    binding.inputDamperLsr.text.isNotEmpty()
                    &&
                    binding.inputDamperLsr.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputDamperCode.text.isNotEmpty() ){
                        val damperCode: Int = binding.inputDamperCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            damperViewModel.listDampers.value?.any {
                                it.code == damperCode
                            }!!
                        ){
                            binding.inputDamperCode.setTextColor(
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
                            binding.inputDamperCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setDamperIntoRepository(damperCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var damperCode = 1
                        while(
                            damperViewModel.listDampers.value?.any {
                                it.code == damperCode
                            }!!
                        ){
                            damperCode++
                        }
                        setDamperIntoRepository(damperCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })


        binding.inputDamperCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(
                        s.toString() != ""
                        &&
                        damperViewModel.listDampers.value?.any {
                            it.code == s.toString().toInt()
                        }!!
                    ) {
                        binding.inputDamperCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    }
                    else{
                        binding.inputDamperCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        if(
                            binding.inputDamperHsr.text.isNotEmpty()
                            &&
                            binding.inputDamperHsc.text.isNotEmpty()
                            &&
                            binding.inputDamperLsr.text.isNotEmpty()
                            &&
                            binding.inputDamperLsc.text.isNotEmpty()
                            &&
                            s.toString() != ""
                        ){
                            val balanceCode = s.toString().toInt()
                            setDamperIntoRepository(balanceCode)
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

    private fun setStockedDamperParameters(dataDamper: MutableLiveData<DataDamper>){
        binding.inputDamperCode.setText(dataDamper.value?.code.toString())
        binding.inputDamperHsr.setText(dataDamper.value?.hsr.toString())
        binding.inputDamperHsc.setText(dataDamper.value?.hsc.toString())
        binding.inputDamperLsr.setText(dataDamper.value?.lsr.toString())
        binding.inputDamperLsc.setText(dataDamper.value?.lsc.toString())
    }


    private fun setDamperIntoRepository(damperCode: Int){
        when(arguments?.getString("BALANCE_POSITION")){
            "Front" -> {
                val newDamper = DataDamper(
                    damperCode,
                    "Front",
                    binding.inputDamperHsr.text.toString().toDouble(),
                    binding.inputDamperHsc.text.toString().toDouble(),
                    binding.inputDamperLsr.text.toString().toDouble(),
                    binding.inputDamperLsc.text.toString().toDouble(),
                )
                damperViewModel.setFrontDamperParametersStocked(newDamper)
            }
            else -> {
                val newDamper = DataDamper(
                    damperCode,
                    "End",
                    binding.inputDamperHsr.text.toString().toDouble(),
                    binding.inputDamperHsc.text.toString().toDouble(),
                    binding.inputDamperLsr.text.toString().toDouble(),
                    binding.inputDamperLsc.text.toString().toDouble(),
                )
                damperViewModel.setBackDamperParametersStocked(newDamper)
            }
        }
    }
}