package com.example.polimarche.users.all.menu.setup.problem

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.solved_problem.DataSolvedProblem
import com.example.polimarche.data_container.problem.solved_problem.SolvedProblemViewModel
import com.example.polimarche.users.all.menu.setup.see.DetailsSetupActivity

class SolvedProblemAdapter(
    private val problemClicked: DataProblem,
    private val solvedProblemViewModel: SolvedProblemViewModel,
    private val listener: OnProblemReappearedClick
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listSolvedProblem: MutableList<DataSolvedProblem> =
        solvedProblemViewModel.filterListByProblemCode(problemClicked.code).value?.toMutableList()!!


    interface OnProblemReappearedClick{
        fun onProblemReappearedClick(element: DataSolvedProblem, itemView: View)
    }

    inner class ViewHolderSolvedProblem(
        solvedProblemView : View
    ) : RecyclerView.ViewHolder(solvedProblemView), View.OnClickListener{
        val setupCode: TextView = solvedProblemView.findViewById(R.id.setupCodeSolvedProblem)
        val description: EditText = solvedProblemView.findViewById(R.id.descriptionSolvedProblem)
        val reappearedProblem: ImageView = solvedProblemView.findViewById(R.id.imageViewReappearedSolvedProblem)
        val visualizeProblem: ImageView = solvedProblemView.findViewById(R.id.imageViewVisualizeSetupSolvedProblem)

        val linearLayoutTouchable: LinearLayout = solvedProblemView.findViewById(R.id.linearLayoutTouchableSolvedProblem)
        val linearLayout: LinearLayout = solvedProblemView.findViewById(R.id.linearLayoutExpandableSolvedProblem)

        init {
            reappearedProblem.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val id = v?.id
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (id == R.id.imageViewReappearedSolvedProblem) {
                    listener.onProblemReappearedClick(
                        /*
                        Find the instance of DataProblem that has the same problem code
                        as the one clicked at the position given by the adapter
                         */
                        solvedProblemViewModel.filterListByProblemCode(
                            problemClicked.code
                        ).value?.get(position)!!,
                        v
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_solved_problem, parent, false
        )
        return ViewHolderSolvedProblem(view)
    }

    override fun getItemCount(): Int {
        return listSolvedProblem.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderSolvedProblem ->{
                holder.apply {
                    setupCode.text = "Setup ${ listSolvedProblem?.get(position)?.setupCode.toString() }"
                    description.setText(listSolvedProblem?.get(position)?.description)
                    reappearedProblem.setImageResource(R.drawable.remove_general_small_icon)
                    visualizeProblem.setImageResource(R.drawable.visibility_icon)

                    visualizeProblem.setOnClickListener {
                        Intent(it.context, DetailsSetupActivity::class.java).apply {
                            this.putExtra("SETUP_CODE", listSolvedProblem?.get(position)?.setupCode)
                            itemView.context.startActivity(this)
                        }
                    }

                    val expansion = listSolvedProblem?.get(position)?.expansion!!
                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    linearLayoutTouchable.setOnClickListener{
                        listSolvedProblem[position].expansion = !listSolvedProblem[position].expansion
                        notifyItemChanged(position)
                    }

                }
            }
        }
    }

    /*
    Firstly it adds to the list inside occurringProblemViewProblem a new OccurringProblem
    in which the problem code is the one of the problem clicked, the
    setup code is the one of the item removed and the description is the one passed
    as a parameter from SolvedProblemFragment.
    Then it calls directly a method of the adapter
    that replaces the list of the adapter.
     */
    suspend fun removeItemFromList(item: DataSolvedProblem, description: String){
        val newList = solvedProblemViewModel.filterListByProblemCode(problemClicked.code).value?.toMutableList()!!

        newList.removeIf { it.setupCode == item.setupCode }

        setNewList(
            newList
        )

        solvedProblemViewModel.removeItemFromList(item, description)
    }

    fun setNewList(newList: MutableList<DataSolvedProblem>){
        listSolvedProblem.clear()
        listSolvedProblem.addAll(newList)
        notifyDataSetChanged()
    }


}