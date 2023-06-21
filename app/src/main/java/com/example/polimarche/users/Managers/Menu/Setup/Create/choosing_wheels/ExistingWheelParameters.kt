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
import com.example.polimarche.databinding.FragmentManagersExistingWheelsParametersBinding
import com.example.polimarche.data_container.wheel.DataWheel
import com.example.polimarche.data_container.wheel.WheelViewModel

class ExistingWheelParameters(
    private val view1: ImageView?, private val view2: ImageView?
 ): Fragment(R.layout.fragment_managers_existing_wheels_parameters) {

    private var _binding: FragmentManagersExistingWheelsParametersBinding? = null
    private val binding get() = _binding!!

    private val wheelViewModel: WheelViewModel by viewModels()

    private var parametersStocked: MutableLiveData<DataWheel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManagersExistingWheelsParametersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewVisibility(binding.inputWheelCode)

        when(arguments?.getString("WHEEL_POSITION")){
            "Front right"-> {
                // Checks if a set of parameters is already stored.
                if( wheelViewModel.getFrontRightParametersStocked() != null ){
                    parametersStocked = wheelViewModel.getFrontRightParametersStocked()
                    // If the code of the parameters stocked is the same as the code of one
                    // element of the list of different balance parameters, then it sets the
                    // text value inside the edit text equals to the code of the set stocked.
                    if (
                        wheelViewModel.listWheel.value?.any{
                            it.code == parametersStocked?.value?.code!!
                        }!!
                    )
                    {
                        binding.inputWheelCode.setText(
                            parametersStocked?.value?.code.toString()
                        )
                    }
                }
            }
            "Front left"-> {
                // Checks if a set of parameters is already stored.
                if( wheelViewModel.getFrontLeftParametersStocked() != null ){
                    parametersStocked = wheelViewModel.getFrontLeftParametersStocked()
                    // If the code of the parameters stocked is the same as the code of one
                    // element of the list of different balance parameters, then it sets the
                    // text value inside the edit text equals to the code of the set stocked.
                    if (
                        wheelViewModel.listWheel.value?.any{
                            it.code == parametersStocked?.value?.code!!
                        }!!
                    )
                    {
                        binding.inputWheelCode.setText(
                            parametersStocked?.value?.code.toString()
                        )
                    }
                }
            }
            "Rear right"-> {
                // Checks if a set of parameters is already stored.
                if( wheelViewModel.getRearRightParametersStocked() != null ){
                    parametersStocked = wheelViewModel.getRearRightParametersStocked()
                    // If the code of the parameters stocked is the same as the code of one
                    // element of the list of different balance parameters, then it sets the
                    // text value inside the edit text equals to the code of the set stocked.
                    if (
                        wheelViewModel.listWheel.value?.any{
                            it.code == parametersStocked?.value?.code!!
                        }!!
                    )
                    {
                        binding.inputWheelCode.setText(
                            parametersStocked?.value?.code.toString()
                        )
                    }
                }
            }
            "Rear left"-> {
                // Checks if a set of parameters is already stored.
                if( wheelViewModel.getRearLeftParametersStocked() != null ){
                    parametersStocked = wheelViewModel.getRearLeftParametersStocked()
                    // If the code of the parameters stocked is the same as the code of one
                    // element of the list of different balance parameters, then it sets the
                    // text value inside the edit text equals to the code of the set stocked.
                    if (
                        wheelViewModel.listWheel.value?.any{
                            it.code == parametersStocked?.value?.code!!
                        }!!
                    )
                    {
                        binding.inputWheelCode.setText(
                            parametersStocked?.value?.code.toString()
                        )
                    }
                }
            }
        }

        binding.inputWheelCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() != "") {
                    when (arguments?.getString("WHEEL_POSITION")) {
                        "Front right" -> {
                            /*
                            Changes the color of the edit text to red when the code given doesn't
                            match any others code of the entire list of parameters and to white
                            when it matches.
                             */
                            if (
                                wheelViewModel.getFrontRightWheels().none {
                                    it.code == s.toString().toInt()
                                }
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
                                /*
                                Sets the value of the front balance parameters used to create a new
                                setup as the same of the set of parameters that has the code equals
                                to the one given by the user as input.
                                 */
                                wheelViewModel.setFrontRightWheelParameters(
                                    wheelViewModel.getFrontRightWheels().filter {
                                        it.code == s.toString().toInt()
                                    }[0]
                                )
                            }
                        }
                        "Front left"-> {
                            /*
                            Changes the color of the edit text to red when the code given doesn't
                            match any others code of the entire list of parameters and to white
                            when it matches.
                             */
                            if (
                                wheelViewModel.getFrontLeftWheels().none {
                                    it.code == s.toString().toInt()
                                }
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
                                /*
                                Sets the value of the front balance parameters used to create a new
                                setup as the same of the set of parameters that has the code equals
                                to the one given by the user as input.
                                 */
                                wheelViewModel.setFrontLeftWheelParameters(
                                    wheelViewModel.getFrontLeftWheels().filter {
                                        it.code == s.toString().toInt()
                                    }[0]
                                )
                            }
                        }
                        "Rear right"-> {
                            /*
                            Changes the color of the edit text to red when the code given doesn't
                            match any others code of the entire list of parameters and to white
                            when it matches.
                             */
                            if (
                                wheelViewModel.getRearRightWheels().none {
                                    it.code == s.toString().toInt()
                                }
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
                                /*
                                Sets the value of the front balance parameters used to create a new
                                setup as the same of the set of parameters that has the code equals
                                to the one given by the user as input.
                                 */
                                wheelViewModel.setRearRightWheelParameters(
                                    wheelViewModel.getRearRightWheels().filter {
                                        it.code == s.toString().toInt()
                                    }[0]
                                )
                            }
                        }
                        "Rear left"-> {
                            /*
                            Changes the color of the edit text to red when the code given doesn't
                            match any others code of the entire list of parameters and to white
                            when it matches.
                             */
                            if (
                                wheelViewModel.getRearLeftWheels().none {
                                    it.code == s.toString().toInt()
                                }
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
                                /*
                                Sets the value of the front balance parameters used to create a new
                                setup as the same of the set of parameters that has the code equals
                                to the one given by the user as input.
                                 */
                                wheelViewModel.setRearLeftWheelParameters(
                                    wheelViewModel.getRearLeftWheels().filter {
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
