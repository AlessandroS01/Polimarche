package com.example.polimarche.Managers.Menu.Setup.Create.ChoosingDampers

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
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentManagersAddSpringsParametersBinding
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.spring.SpringViewModel

class AddSpringsParameters(
    private val view1: ImageView?, private val view2: ImageView?
): Fragment(R.layout.fragment_managers_add_springs_parameters) {

    private var _binding: FragmentManagersAddSpringsParametersBinding? = null
    private val binding get() = _binding!!

    private val springViewModel: SpringViewModel by viewModels()

    private var parametersStocked: MutableLiveData<DataSpring>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersAddSpringsParametersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewVisibility(binding.inputSpringCode)
        setViewVisibility(binding.inputSpringCodification)
        setViewVisibility(binding.inputSpringHeight)
        setViewVisibility(binding.inputSpringArbStiffness)
        setViewVisibility(binding.inputSpringArbPosition)

        // Checks if the new set of parameters are for front or back end.
        // If already exists then it sets the text value of the 3 different edit text.
        when(arguments?.getString("SPRING_POSITION")){
            "Front"-> {
                /*
                 Sets the value of parameterStocked if inside the repository there is already
                 a set of parameter set.
                 */
                if( springViewModel.getFrontSpringParametersStocked() != null ){
                    parametersStocked = springViewModel.getFrontSpringParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        springViewModel.listSpring.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedSpringParameters(parametersStocked!!)
                    }
                }
            }
            else -> {
                /*
                 Sets the value of parameterStocked if inside the repository there is already
                 a set of parameter set.
                 */
                if( springViewModel.getBackSpringParametersStocked() != null ){
                    parametersStocked = springViewModel.getBackSpringParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        springViewModel.listSpring.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedSpringParameters(parametersStocked!!)
                    }
                }
            }
        }


        binding.inputSpringCodification.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputSpringCode.text.isNotEmpty()) {
                    val springCode: Int = binding.inputSpringCode.text.toString().toInt()
                    if (
                        springViewModel.listSpring.value?.any {
                            it.code == springCode
                        }!!
                    ) {
                        binding.inputSpringCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    } else {
                        binding.inputSpringCode.setTextColor(
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
                    binding.inputSpringHeight.text.isNotEmpty()
                    &&
                    binding.inputSpringArbStiffness.text.isNotEmpty()
                    &&
                    binding.inputSpringArbPosition.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputSpringCode.text.isNotEmpty() ){
                        val springCode: Int = binding.inputSpringCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            springViewModel.listSpring.value?.any {
                                it.code == springCode
                            }!!
                        ){
                            binding.inputSpringCode.setTextColor(
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
                            binding.inputSpringCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setSpringIntoRepository(springCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var springCode = 1
                        while(
                            springViewModel.listSpring.value?.any {
                                it.code == springCode
                            }!!
                        ){
                            springCode++
                        }
                        setSpringIntoRepository(springCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.inputSpringHeight.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputSpringCode.text.isNotEmpty()) {
                    val springCode: Int = binding.inputSpringCode.text.toString().toInt()
                    if (
                        springViewModel.listSpring.value?.any {
                            it.code == springCode
                        }!!
                    ) {
                        binding.inputSpringCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    } else {
                        binding.inputSpringCode.setTextColor(
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
                    binding.inputSpringCodification.text.isNotEmpty()
                    &&
                    binding.inputSpringArbStiffness.text.isNotEmpty()
                    &&
                    binding.inputSpringArbPosition.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputSpringCode.text.isNotEmpty() ){
                        val springCode: Int = binding.inputSpringCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            springViewModel.listSpring.value?.any {
                                it.code == springCode
                            }!!
                        ){
                            binding.inputSpringCode.setTextColor(
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
                            binding.inputSpringCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setSpringIntoRepository(springCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var springCode = 1
                        while(
                            springViewModel.listSpring.value?.any {
                                it.code == springCode
                            }!!
                        ){
                            springCode++
                        }
                        setSpringIntoRepository(springCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.inputSpringArbStiffness.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputSpringCode.text.isNotEmpty()) {
                    val springCode: Int = binding.inputSpringCode.text.toString().toInt()
                    if (
                        springViewModel.listSpring.value?.any {
                            it.code == springCode
                        }!!
                    ) {
                        binding.inputSpringCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    } else {
                        binding.inputSpringCode.setTextColor(
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
                    binding.inputSpringCodification.text.isNotEmpty()
                    &&
                    binding.inputSpringHeight.text.isNotEmpty()
                    &&
                    binding.inputSpringArbPosition.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputSpringCode.text.isNotEmpty() ){
                        val springCode: Int = binding.inputSpringCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            springViewModel.listSpring.value?.any {
                                it.code == springCode
                            }!!
                        ){
                            binding.inputSpringCode.setTextColor(
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
                            binding.inputSpringCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setSpringIntoRepository(springCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var springCode = 1
                        while(
                            springViewModel.listSpring.value?.any {
                                it.code == springCode
                            }!!
                        ){
                            springCode++
                        }
                        setSpringIntoRepository(springCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.inputSpringArbPosition.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*
                Changes the text color of the balance code edit text to red when the code
                inserted is already used by one of the other set of parameters stored, and to white
                when that's not happening.
                 */
                if( binding.inputSpringCode.text.isNotEmpty()) {
                    val springCode: Int = binding.inputSpringCode.text.toString().toInt()
                    if (
                        springViewModel.listSpring.value?.any {
                            it.code == springCode
                        }!!
                    ) {
                        binding.inputSpringCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    } else {
                        binding.inputSpringCode.setTextColor(
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
                    binding.inputSpringCodification.text.isNotEmpty()
                    &&
                    binding.inputSpringHeight.text.isNotEmpty()
                    &&
                    binding.inputSpringArbStiffness.text.isNotEmpty()
                    &&
                    s.toString() != ""
                ){
                    if( binding.inputSpringCode.text.isNotEmpty() ){
                        val springCode: Int = binding.inputSpringCode.text.toString().toInt()
                        /*
                        Checks if the code is already used by other elements of the entire list
                        of balance parameters.
                         */
                        if(
                            springViewModel.listSpring.value?.any {
                                it.code == springCode
                            }!!
                        ){
                            binding.inputSpringCode.setTextColor(
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
                            binding.inputSpringCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            setSpringIntoRepository(springCode)
                        }
                    }
                    /*
                    If the user doesn't give a value to balance code edit text then it
                    finds the first integer that is not used by the other elements of the list.
                     */
                    else{
                        var springCode = 1
                        while(
                            springViewModel.listSpring.value?.any {
                                it.code == springCode
                            }!!
                        ){
                            springCode++
                        }
                        setSpringIntoRepository(springCode)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.inputSpringCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(
                        s.toString() != ""
                        &&
                        springViewModel.listSpring.value?.any {
                            it.code == s.toString().toInt()
                        }!!
                    ) {
                        binding.inputSpringCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    }
                    else{
                        binding.inputSpringCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        if(
                            binding.inputSpringCodification.text.isNotEmpty()
                            &&
                            binding.inputSpringHeight.text.isNotEmpty()
                            &&
                            binding.inputSpringArbStiffness.text.isNotEmpty()
                            &&
                            binding.inputSpringArbPosition.text.isNotEmpty()
                            &&
                            s.toString() != ""
                        ){
                            val springCode = s.toString().toInt()
                            setSpringIntoRepository(springCode)
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

    private fun setStockedSpringParameters(dataSpring: MutableLiveData<DataSpring>){
        binding.inputSpringCode.setText(dataSpring.value?.code.toString())
        binding.inputSpringCodification.setText(dataSpring.value?.codification.toString())
        binding.inputSpringHeight.setText(dataSpring.value?.height.toString())
        binding.inputSpringArbStiffness.setText(dataSpring.value?.arb_stiffness.toString())
        binding.inputSpringArbPosition.setText(dataSpring.value?.arb_position.toString())
    }


    private fun setSpringIntoRepository(springCode: Int){
        when(arguments?.getString("SPRING_POSITION")){
            "Front" -> {
                val newSpring = DataSpring(
                    springCode,
                    binding.inputSpringCodification.text.toString(),
                    "Front",
                    binding.inputSpringHeight.text.toString().toDouble(),
                    binding.inputSpringArbStiffness.text.toString(),
                    binding.inputSpringArbPosition.text.toString(),
                )
                springViewModel.setFrontSpringParametersStocked(newSpring)
            }
            else -> {
                val newSpring = DataSpring(
                    springCode,
                    binding.inputSpringCodification.text.toString(),
                    "End",
                    binding.inputSpringHeight.text.toString().toDouble(),
                    binding.inputSpringArbStiffness.text.toString(),
                    binding.inputSpringArbPosition.text.toString(),
                )
                springViewModel.setBackSpringParametersStocked(newSpring)
            }
        }
    }
}