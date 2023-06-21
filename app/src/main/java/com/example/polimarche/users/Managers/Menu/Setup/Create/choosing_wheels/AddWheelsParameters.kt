package com.example.polimarche.users.managers.menu.setup.create.choosing_wheels

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
import com.example.polimarche.databinding.FragmentManagersAddWheelsParametersBinding
import com.example.polimarche.data_container.wheel.DataWheel
import com.example.polimarche.data_container.wheel.WheelViewModel

class AddWheelsParameters(
    private val view1: ImageView?, private val view2: ImageView?
): Fragment(R.layout.fragment_managers_add_wheels_parameters) {

    private var _binding: FragmentManagersAddWheelsParametersBinding? = null
    private val binding get() = _binding!!

    private val wheelViewModel: WheelViewModel by viewModels()

    private var parametersStocked: MutableLiveData<DataWheel>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersAddWheelsParametersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewVisibility(binding.inputWheelCode)
        setViewVisibility(binding.inputWheelCodification)
        setViewVisibility(binding.inputWheelPressure)
        setViewVisibility(binding.inputWheelCamber)
        setViewVisibility(binding.inputWheelToe)


        when(arguments?.getString("WHEEL_POSITION")){
            "Front right"-> {
                /*
                 Sets the value of parameterStocked if inside the repository there is already
                 a set of parameter set.
                 */
                if( wheelViewModel.getFrontRightParametersStocked() != null ){
                    parametersStocked = wheelViewModel.getFrontRightParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        wheelViewModel.listWheel.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedBalanceParameters(parametersStocked!!)
                    }
                }
            }
            "Front left"-> {
                /*
                 Sets the value of parameterStocked if inside the repository there is already
                 a set of parameter set.
                 */
                if( wheelViewModel.getFrontLeftParametersStocked() != null ){
                    parametersStocked = wheelViewModel.getFrontLeftParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        wheelViewModel.listWheel.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedBalanceParameters(parametersStocked!!)
                    }
                }
            }
            "Rear right"-> {
                /*
                 Sets the value of parameterStocked if inside the repository there is already
                 a set of parameter set.
                 */
                if( wheelViewModel.getRearRightParametersStocked() != null ){
                    parametersStocked = wheelViewModel.getRearRightParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        wheelViewModel.listWheel.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedBalanceParameters(parametersStocked!!)
                    }
                }
            }
            "Rear left"-> {
                /*
                 Sets the value of parameterStocked if inside the repository there is already
                 a set of parameter set.
                 */
                if( wheelViewModel.getRearLeftParametersStocked() != null ){
                    parametersStocked = wheelViewModel.getRearLeftParametersStocked()
                    /*
                    Checks if the code of the parameters already stocked is referring to
                    a set of parameters that are already stored inside the list of parameters.
                    If it's not true then it sets the value of the different edit texts
                    the corresponding value of the stored parameters.
                     */
                    if(
                        wheelViewModel.listWheel.value?.none {
                            it.code == parametersStocked!!.value?.code
                        }!!
                    ){
                        setStockedBalanceParameters(parametersStocked!!)
                    }
                }
            }
        }

        binding.inputWheelCodification.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if( binding.inputWheelCode.text.isNotEmpty() ) {

                        val wheelCode: Int = binding.inputWheelCode.text.toString().toInt()
                        if (
                            wheelViewModel.listWheel.value?.any {
                                it.code == wheelCode
                            }!!
                        ) {
                            binding.inputWheelCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                        } else {
                            binding.inputWheelCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                        }
                    }
                    if(
                        binding.inputWheelPressure.text.isNotEmpty()
                        &&
                        binding.inputWheelCamber.text.isNotEmpty()
                        &&
                        binding.inputWheelToe.text.isNotEmpty()
                        &&
                        s.toString() != ""
                    ){
                        if( binding.inputWheelCode.text.isNotEmpty() ){
                            val wheelCode: Int = binding.inputWheelCode.text.toString().toInt()
                            if(
                                wheelViewModel.listWheel.value?.any {
                                    it.code == wheelCode
                                }!!
                            ){
                                binding.inputWheelCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red_700
                                    )
                                )
                            }
                            else {
                                binding.inputWheelCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                setWheelIntoRepository(wheelCode)
                            }
                        }
                        else{
                            var wheelCode = 1
                            while(
                                wheelViewModel.listWheel.value?.any {
                                    it.code == wheelCode
                                }!!
                            ){
                                wheelCode++
                            }
                            setWheelIntoRepository(wheelCode)
                        }
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        // The operations of the following code is similar to the previous one.
        binding.inputWheelPressure.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if( binding.inputWheelCode.text.isNotEmpty() ) {

                        val wheelCode: Int = binding.inputWheelCode.text.toString().toInt()
                        if (
                            wheelViewModel.listWheel.value?.any {
                                it.code == wheelCode
                            }!!
                        ) {
                            binding.inputWheelCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                        } else {
                            binding.inputWheelCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                        }
                    }
                    if(
                        binding.inputWheelCodification.text.isNotEmpty()
                        &&
                        binding.inputWheelCamber.text.isNotEmpty()
                        &&
                        binding.inputWheelToe.text.isNotEmpty()
                        &&
                        s.toString() != ""
                    ){
                        if( binding.inputWheelCode.text.isNotEmpty() ){
                            val wheelCode: Int = binding.inputWheelCode.text.toString().toInt()
                            if(
                                wheelViewModel.listWheel.value?.any {
                                    it.code == wheelCode
                                }!!
                            ){
                                binding.inputWheelCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red_700
                                    )
                                )
                            }
                            else {
                                binding.inputWheelCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                setWheelIntoRepository(wheelCode)
                            }
                        }
                        else{
                            var wheelCode = 1
                            while(
                                wheelViewModel.listWheel.value?.any {
                                    it.code == wheelCode
                                }!!
                            ){
                                wheelCode++
                            }
                            setWheelIntoRepository(wheelCode)
                        }
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        binding.inputWheelCamber.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if( binding.inputWheelCode.text.isNotEmpty() ) {

                        val wheelCode: Int = binding.inputWheelCode.text.toString().toInt()
                        if (
                            wheelViewModel.listWheel.value?.any {
                                it.code == wheelCode
                            }!!
                        ) {
                            binding.inputWheelCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                        } else {
                            binding.inputWheelCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                        }
                    }
                    if(
                        binding.inputWheelPressure.text.isNotEmpty()
                        &&
                        binding.inputWheelCodification.text.isNotEmpty()
                        &&
                        binding.inputWheelToe.text.isNotEmpty()
                        &&
                        s.toString() != ""
                    ){
                        if( binding.inputWheelCode.text.isNotEmpty() ){
                            val wheelCode: Int = binding.inputWheelCode.text.toString().toInt()
                            if(
                                wheelViewModel.listWheel.value?.any {
                                    it.code == wheelCode
                                }!!
                            ){
                                binding.inputWheelCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red_700
                                    )
                                )
                            }
                            else {
                                binding.inputWheelCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                setWheelIntoRepository(wheelCode)
                            }
                        }
                        else{
                            var wheelCode = 1
                            while(
                                wheelViewModel.listWheel.value?.any {
                                    it.code == wheelCode
                                }!!
                            ){
                                wheelCode++
                            }
                            setWheelIntoRepository(wheelCode)
                        }
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        binding.inputWheelToe.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if( binding.inputWheelCode.text.isNotEmpty() ) {

                        val wheelCode: Int = binding.inputWheelCode.text.toString().toInt()
                        if (
                            wheelViewModel.listWheel.value?.any {
                                it.code == wheelCode
                            }!!
                        ) {
                            binding.inputWheelCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red_700
                                )
                            )
                        } else {
                            binding.inputWheelCode.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                        }
                    }
                    if(
                        binding.inputWheelPressure.text.isNotEmpty()
                        &&
                        binding.inputWheelCodification.text.isNotEmpty()
                        &&
                        binding.inputWheelCamber.text.isNotEmpty()
                        &&
                        s.toString() != ""
                    ){
                        if( binding.inputWheelCode.text.isNotEmpty() ){
                            val wheelCode: Int = binding.inputWheelCode.text.toString().toInt()
                            if(
                                wheelViewModel.listWheel.value?.any {
                                    it.code == wheelCode
                                }!!
                            ){
                                binding.inputWheelCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red_700
                                    )
                                )
                            }
                            else {
                                binding.inputWheelCode.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                setWheelIntoRepository(wheelCode)
                            }
                        }
                        else{
                            var wheelCode = 1
                            while(
                                wheelViewModel.listWheel.value?.any {
                                    it.code == wheelCode
                                }!!
                            ){
                                wheelCode++
                            }
                            setWheelIntoRepository(wheelCode)
                        }
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        binding.inputWheelCode.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(
                        s.toString() != ""
                        &&
                        wheelViewModel.listWheel.value?.any {
                            it.code == s.toString().toInt()
                        }!!
                    ) {
                        binding.inputWheelCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_700
                            )
                        )
                    }
                    else{
                        binding.inputWheelCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        if(
                            binding.inputWheelCodification.text.isNotEmpty()
                            &&
                            binding.inputWheelPressure.text.isNotEmpty()
                            &&
                            binding.inputWheelCamber.text.isNotEmpty()
                            &&
                            binding.inputWheelToe.text.isNotEmpty()
                            &&
                            s.toString() != ""
                        ){
                            val wheelCode = s.toString().toInt()
                            setWheelIntoRepository(wheelCode)
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


    private fun setStockedBalanceParameters(dataWheel: MutableLiveData<DataWheel>){
        binding.inputWheelCode.setText(dataWheel.value?.code.toString())
        binding.inputWheelCodification.setText(dataWheel.value?.codification.toString())
        binding.inputWheelPressure.setText(dataWheel.value?.pressure.toString())
        binding.inputWheelCamber.setText(dataWheel.value?.camber.toString())
        binding.inputWheelToe.setText(dataWheel.value?.toe.toString())
    }
    
    
    private fun setWheelIntoRepository(wheelCode: Int){
        when(arguments?.getString("WHEEL_POSITION")){
            "Front right" -> {
                val newWheel = DataWheel(
                    wheelCode,
                    "Front right",
                    binding.inputWheelCodification.text.toString(),
                    "${binding.inputWheelPressure.text} bar",
                    binding.inputWheelCamber.text.toString(),
                    binding.inputWheelToe.text.toString(),
                )
                wheelViewModel.setFrontRightWheelParameters(newWheel)
            }
            "Front left"-> {
                val newWheel = DataWheel(
                    wheelCode,
                    "Front left",
                    binding.inputWheelCodification.text.toString(),
                    binding.inputWheelPressure.text.toString(),
                    binding.inputWheelCamber.text.toString(),
                    binding.inputWheelToe.text.toString(),
                )
                wheelViewModel.setFrontLeftWheelParameters(newWheel)
            }
            "Rear right"-> {
                val newWheel = DataWheel(
                    wheelCode,
                    "Rear right",
                    binding.inputWheelCodification.text.toString(),
                    binding.inputWheelPressure.text.toString(),
                    binding.inputWheelCamber.text.toString(),
                    binding.inputWheelToe.text.toString(),
                )
                wheelViewModel.setRearRightWheelParameters(newWheel)
            }
            "Rear left"-> {
                val newWheel = DataWheel(
                    wheelCode,
                    "Rear left",
                    binding.inputWheelCodification.text.toString(),
                    binding.inputWheelPressure.text.toString(),
                    binding.inputWheelCamber.text.toString(),
                    binding.inputWheelToe.text.toString(),
                )
                wheelViewModel.setRearLeftWheelParameters(newWheel)
            }

        }
    }
}