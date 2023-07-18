package com.example.polimarche.users.managers.menu.setup.delete

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
import com.example.polimarche.R
import com.example.polimarche.databinding.FragmentManagersDeleteSetupVisualizeSetupBinding
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VisualizeSetupFragment(
    val adapter: DeleteSetupAdapter,
    window: Window
) : Fragment(R.layout.fragment_managers_delete_setup_visualize_setup) {

    private val setupViewModel: SetupViewModel by viewModels()

    private lateinit var setupClicked: DataSetup

    private val window = window

    private var _binding: FragmentManagersDeleteSetupVisualizeSetupBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagersDeleteSetupVisualizeSetupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel.initialize()
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)


        val setupCode = arguments?.getInt("SETUP_CODE")
        setupViewModel.setupList.observe(viewLifecycleOwner) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            setupClicked =
                setupViewModel.setupList.value?.filter { it.code == setupCode }?.toMutableList()
                    ?.get(0)!!

            var notes = ""
            setupClicked.notes.forEachIndexed { index, s ->
                notes += if (index == setupClicked.notes.size - 1) s else "${s}\n"
            }
            if (notes == "") notes = "No notes"
            binding.setupClicked = setupClicked
            binding.notesSetupClicked = notes
        }
        /*
        Chiude il frammento quando un utente fa clic sulla Image view posizionata
        nell'angolo in alto a destra.

         */
        binding.imageViewCloseVisualizationSetup.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        /*
        Rimuove l'elemento cliccato se l'utente conferma la sua decisione
        all'interno di una finestra di dialogo che apparirà al suo stesso click
        sull'image View posizionata in basso a destra di questo frammento.

         */
        binding.imageViewDeleteSetup.setOnClickListener {
            showDialog()
        }
    }

    /*
    Creare una finestra di dialogo che consenta all'utente di annullare o confermare il
    cancellazione del setup.
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_mangers_deletion_setup)

        dialog.findViewById(R.id.textViewTitleAllUses) as TextView
        val confirmDeletion = dialog.findViewById(R.id.confirmDeletionSetup) as FrameLayout
        val cancelDeletion = dialog.findViewById(R.id.cancelDeletionSetup) as FrameLayout
        /*
        Conferma la cancellazione con la rimozione dell'elemento all'interno della lista e chiama l'adapter
         per informarlo che l'elemento è stato rimosso.
         */
        confirmDeletion.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                // Chiama la funzione deleteSetup e attende che finisca
                setupViewModel.deleteSetup(setupClicked)

                // Continua con il codice rimanente al termine dell'eliminazione del setup
                adapter.setNewList(setupViewModel.setupList.value?.toMutableList()!!)
                parentFragmentManager.beginTransaction().remove(this@VisualizeSetupFragment).commit()
                dialog.dismiss()
            }
        }
        cancelDeletion.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}

