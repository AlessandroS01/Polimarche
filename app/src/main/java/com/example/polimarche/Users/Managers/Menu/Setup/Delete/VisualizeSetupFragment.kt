package com.example.polimarche.Users.Managers.Menu.Setup.Delete

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentManagersDeleteSetupVisualizeSetupBinding
import com.example.polimarche.Users.Managers.Adapters.DeleteSetupAdapter
import com.example.polimarche.Users.All.Menu.Setup.Problem.SetupViewModel

class VisualizeSetupFragment(
    private val clickedSetupPosition: Int,
    val adapter: DeleteSetupAdapter
) : Fragment(R.layout.fragment_managers_delete_setup_visualize_setup) {

    private val setupViewModel: SetupViewModel by viewModels()

    private var _binding: FragmentManagersDeleteSetupVisualizeSetupBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagersDeleteSetupVisualizeSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        /*
        Closes the fragment when a user click on the Image view positioned
        in the upper right corner.
         */
        binding.imageViewCloseVisualizationSetup.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        /*
        Removes the item clicked if the user confirms his/her decision
        inside a dialog box that will appear after himself/herself click
        on the the image View positioned on the bottom right of this fragment.
         */
        binding.imageViewDeleteSetup.setOnClickListener {
            showDialog()
        }
    }

    /*
    Create a dialog box that let the user cancel or confirm the
    setup deletion.
     */
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_managers_confirm_delete_setup)

        dialog.findViewById(R.id.textViewBody) as TextView
        val confirmDeletion = dialog.findViewById(R.id.confirmDeletionSetup) as FrameLayout
        val cancelDeletion = dialog.findViewById(R.id.cancelDeletionSetup) as FrameLayout
        /*
        Confirm the deletion with the removal of the item inside the list and the call to the
        adapter to let it know that the item was removed.
         */
        confirmDeletion.setOnClickListener {
            setupViewModel.deleteSetup(clickedSetupPosition)
            adapter.notifyItemRemoved(clickedSetupPosition)
            parentFragmentManager.beginTransaction().remove(this).commit()
            dialog.dismiss()
        }
        cancelDeletion.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}