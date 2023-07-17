package com.example.polimarche.users.all.menu.setup.problem

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.occurring_problem.DataOccurringProblem
import com.example.polimarche.data_container.problem.occurring_problem.OccurringProblemViewModel
import com.example.polimarche.data_container.problem.solved_problem.SolvedProblemViewModel
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel
import com.example.polimarche.databinding.FragmentGeneralSetupOccurringProblemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore

class OccurringProblemFragment(
    private val problemClicked: DataProblem,
    window: Window
): Fragment(R.layout.fragment_general_setup_occurring_problem),
    OccurringProblemAdapter.OnProblemSolvedClick
{

    private val setupViewModel: SetupViewModel by viewModels()
    private val occurringProblemViewModel: OccurringProblemViewModel by viewModels()
    private val solvedProblemViewModel: SolvedProblemViewModel by viewModels()


    private var _binding: FragmentGeneralSetupOccurringProblemBinding? = null
    private val binding get()= _binding!!

    private val window = window


    private lateinit var occurringProblemAdapter: OccurringProblemAdapter
    private lateinit var recyclerViewOccurringProblem: RecyclerView

    /*
    Elenco dei setups in cui il problema cliccato non è occorrente e non è stato risolto.
     */
    private lateinit var adapterAddOccurringProblems: AddNewOccurringProblemAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneralSetupOccurringProblemBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        Passa all'adattatore l'elenco dei problemi che corrisponde al codice del problema selezionato
        su ProblemiSetupFragment.
         */

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)


        occurringProblemViewModel.listOccurringProblem.observe(viewLifecycleOwner) {
            occurringProblemAdapter = OccurringProblemAdapter(
                problemClicked,
                occurringProblemViewModel,
                this
            )
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            recyclerViewOccurringProblem = binding.listOccurringProblemSetup
            val linearLayoutManagerMain = LinearLayoutManager(this.context)
            recyclerViewOccurringProblem.layoutManager = linearLayoutManagerMain
            recyclerViewOccurringProblem.adapter = occurringProblemAdapter
        }

        binding.imageButtonAddOccurringProblem.setOnClickListener {

            // Recupera i dati e popola listSetupsWithoutProblem
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            val listSetupsWithoutProblem = findSetupsWithoutProblem()

            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            showDialogAddOccurringProblem(listSetupsWithoutProblem)


        }
    }

    /*
    Crea una finestra di dialogo che consente all'utente di aggiungere un nuovo setup in cui il problema
    selezionato è occorrente.
     */
    private fun showDialogAddOccurringProblem(listSetupsWithoutProblem: MutableList<DataSetup>) {
        val dialog = Dialog(requireContext())
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_add_occurring_problem)

        val textView: TextView = dialog.findViewById(R.id.textViewChanges)

        val confirmAddition: FrameLayout = dialog.findViewById(R.id.confirmAddNewOccurringProblem)
        val closeAddition: FrameLayout = dialog.findViewById(R.id.closeAddNewOccurringProblem)

        val recyclerViewAddOccurringProblem: RecyclerView = dialog.findViewById(
            R.id.listAddNewOccurringProblem
        )
        /*
        Controlla se ci sono altri setup in cui il problema può verificarsi.
        Se non ci sono, verrà mostrato un toast.
         */
        if (listSetupsWithoutProblem.size == 0){
            textView.text = "There isn't any setup left."
            confirmAddition.visibility = View.GONE
        }

        occurringProblemViewModel.listOccurringProblem.observe(viewLifecycleOwner) {
            adapterAddOccurringProblems = AddNewOccurringProblemAdapter(listSetupsWithoutProblem)
            val linearLayoutManagerAddProblem = LinearLayoutManager(this.context)
            recyclerViewAddOccurringProblem.layoutManager = linearLayoutManagerAddProblem
            recyclerViewAddOccurringProblem.adapter = adapterAddOccurringProblems
        }

        /*
        Il pulsante di conferma controlla se la checkbox all'interno della recycler view
        è checkata o meno, utilizzando una mappa creata all'interno dell'adapter stesso.
        Se è spuntato legge prima il codice del setup all'interno di listSetupAddOccurringProblem
        nella stessa posizione della checkbox spuntata e, dopo, legge il valore della descrizione
        data utilizzando, come per il checkBox, la mappa creata all'interno dell'adapter che si mantiene aggiornata
        con tutte le descrizioni per ogni setup mostrato.
         */
        confirmAddition.setOnClickListener {

            adapterAddOccurringProblems.getListCheckedElements().filter {
                it.value // this value is set to true when the a checkbox is clicked
            }.forEach {
                /*
                Per ogni setup collegato a una checkbox spuntata viene aggiunto, direttamente al repository,
                chiamando prima un metodo dell'adattatore e successivamente un metodo del viewModel,
                il nuovo problema creato grazie al testo di modifica della descrizione che appare ogni volta
                l'utente spunta una checkbox.

                 */
                val newOccurringProblem =
                    DataOccurringProblem(
                        problemClicked.code,
                        it.key.code,
                        adapterAddOccurringProblems.getListDescriptionElements().getValue(it.key)
                )
                occurringProblemAdapter.addItemToItemView(newOccurringProblem)
            }
            Toast.makeText(this.context, "New setups facing the problem added", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        closeAddition.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    /*
    Ogni volta che l'utente fa clic su removeItem, viene visualizzata la finestra di dialogo
    dopo la chiamata al metodo showDialogRemovingProblem.
     */
    override fun onProblemSolvedClick(element: DataOccurringProblem, itemView: View) {
        showDialogRemovingProblem(element, itemView)
    }

    /*
    Crea una finestra di dialogo che consenta all'utente di aggiungere una descrizione
    se ha cliccato su un problema che è ricomparso
     */
    private fun showDialogRemovingProblem(element: DataOccurringProblem, view: View) {
        val dialog = Dialog(view.context)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_all_uses)

        val descriptionNewSolvedProblem = dialog.findViewById(R.id.editTextAllUses) as EditText
        val changeableText = dialog.findViewById(R.id.textViewChangeableAllUses) as TextView

        changeableText.text = "If the problem is solved click on confirm otherwise click on cancel.\nDescription value is not compulsory."

        val confirmReappearedProblem = dialog.findViewById(R.id.confirmFrameAllUses) as FrameLayout
        val cancelReappearedProblem = dialog.findViewById(R.id.cancelFrameAllUses) as FrameLayout

        confirmReappearedProblem.setOnClickListener {
            /*
            Chiama il metodo removeItemFromList dell'adattatore e passa
            l'elemento cliccato e la descrizione del problema futuro risolto.
             */

            viewLifecycleOwner.lifecycleScope.launch{
                occurringProblemAdapter.removeItemFromList(
                    element,
                    descriptionNewSolvedProblem.text.toString()
                )
            }

            dialog.dismiss()
        }
        cancelReappearedProblem.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

     /*
     Restituisce una lista in cui non sono già memorizzate tutti i diversi setup in
    listOccurringProblem.
    Quindi analizza tutti i setup memorizzati e poi restituisce quelli che non sono all'interno
    l'elenco menzionato prima. Inoltre controlla se il setup ha già risolto il problema.

    Returns a list in which all the different setups are not already stored inside
    listOccurringProblem.
    So it analyzes all the setups stored and then it returns the ones that are not inside
    the list mentioned 2 lines above. Moreover checks if the setup has already fixed the
    problem.
     */
    private fun findSetupsWithoutProblem(): MutableList<DataSetup>{
         val listSetupsWithoutProblem = mutableListOf<DataSetup>()

         val listOccurringProblems = occurringProblemViewModel.listOccurringProblem.value

         val listSolvedProblems = solvedProblemViewModel.listSolvedProblem.value


         // Esegue l'osservazione sul thread principale sul LiveData setupList
         setupViewModel.setupList.observe(viewLifecycleOwner){listSetups ->

             listSetups?.forEach { dataSetup ->
                 val isOccurringProblem = listOccurringProblems?.any {
                     dataSetup.code == it.setupCode && it.problemCode == problemClicked.code
                 } ?: false

                 val isSolvedProblem = listSolvedProblems?.any {
                     dataSetup.code == it.setupCode && it.problemCode == problemClicked.code
                 } ?: false

                 if (!isOccurringProblem && !isSolvedProblem) {
                     listSetupsWithoutProblem.add(dataSetup)
                 }
             }
         }

         return listSetupsWithoutProblem


    }

    override fun onResume() {
        super.onResume()

        occurringProblemViewModel.initialize()
    }

}