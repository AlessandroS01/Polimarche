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
            It sets the click listener only when the user touches manageProblem textView
             */
            manageProblem.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val id = v?.id
            val position : Int = adapterPosition
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
    This method changes the list of items of the recyclerView
    based on the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<DataProblem>){
        this.problemList = filteredList
        notifyDataSetChanged()
    }

    /*
        This method changes the list of items of the recyclerView
     */
    fun setList(newList: MutableList<DataProblem>){
        this.problemList = newList
        notifyDataSetChanged()
    }



}