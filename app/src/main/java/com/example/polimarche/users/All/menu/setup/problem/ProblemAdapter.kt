package com.example.polimarche.users.all.menu.setup.problem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.problem.DataProblem

class ProblemAdapter(
    private var problemList: MutableList<DataProblem> = mutableListOf(),
    private val listener: OnManageProblemClick
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // Definisce un modo per gestire il clic su un problema
    interface OnManageProblemClick{
        fun onManageProblemClick(problemClicked: DataProblem)
    }


    inner class ViewHolderProblem(problemView : View) : RecyclerView.ViewHolder(problemView), View.OnClickListener{
        val problemCode: TextView = problemView.findViewById(R.id.problemCode)
        val problemDescription: TextView = problemView.findViewById(R.id.problemDescription)
        private val manageProblem: TextView = problemView.findViewById(R.id.openManageProblemSetup)


        val linearLayout: LinearLayout = problemView.findViewById(R.id.linearLayoutExpandableProblem)
        val constraintLayout: androidx.constraintlayout.widget.ConstraintLayout = problemView.findViewById(R.id.costraintLayoutProblem)

        init {
            /*
           Imposta il click listener solo quando l'utente tocca manageProblem textView
             */
            manageProblem.setOnClickListener(this)
        }

        /* la funzione onClick viene chiamata quando si verifica un clic su un elemento all'interno
         della RecyclerView.
        Se l'elemento corrisponde all'ID R.id.openManageProblemSetup,
        viene richiamato il metodo onManageProblemClick del listener
        passando l'oggetto DataProblem corrispondente al problema cliccato.*/
        override fun onClick(v: View?) {
            val id = v?.id
            val position : Int = adapterPosition
            //Rappresenta una posizione non valida all'interno di una RecyclerView
            //se position è diversa da RecyclerView.NO_POSITION, significa che la posizione
            // ha un valore valido all'interno della RecyclerView.
            if (position != RecyclerView.NO_POSITION) {
                if (id == R.id.openManageProblemSetup) {
                    listener.onManageProblemClick(problemList[position])
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_general_setup_problem, parent, false)
        return ViewHolderProblem(view)
    }

    override fun getItemCount(): Int {
        return problemList.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderProblem ->{
                holder.apply {
                    problemCode.text = problemList[position].code.toString()
                    problemDescription.text = problemList[position].description

                    val expansion = problemList[position].expansion

                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    constraintLayout.setOnClickListener {
                        problemList[position].expansion = !problemList[position].expansion
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    /*
    Questo metodo modifica l'elenco di elementi della recyclerView
    in base al testo inserito all'interno di SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<DataProblem>){
        this.problemList = filteredList
        notifyDataSetChanged()
    }

    /*
        Questo metodo modifica l'elenco di elementi di recyclerView
     */
    fun setList(newList: MutableList<DataProblem>){
        this.problemList = newList
        notifyDataSetChanged()
    }



}