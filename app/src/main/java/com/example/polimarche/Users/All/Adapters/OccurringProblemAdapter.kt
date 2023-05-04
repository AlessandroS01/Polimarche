package com.example.polimarche.Users.All.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataOccurringProblem
import com.example.polimarche.Data.DataProblem
import com.example.polimarche.Users.All.Menu.Setup.Problem.OccurringProblemViewModel
import com.example.polimarche.Users.All.Menu.Setup.See.DetailsSetupActivity
/*
Passes directly the list of problems that matches with the problem clicked
on ProblemsSetupFragment and then the viewModel to
 */
class OccurringProblemAdapter(
    private val problemClicked: DataProblem,
    private var occurringProblemViewModel: OccurringProblemViewModel,
    private val listener: OnProblemSolvedClick
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listOccurringProblem = occurringProblemViewModel.filterListByProblemCode(
                                            problemClicked.code
                                        )

    interface OnProblemSolvedClick{
        fun onProblemSolvedClick(element: DataOccurringProblem, itemView: View, position: Int)
    }

    inner class ViewHolderOccurringProblem(
        occurringProblemView : View
    ) : RecyclerView.ViewHolder(occurringProblemView), View.OnClickListener{
        val setupCode: TextView = occurringProblemView.findViewById(R.id.setupCodeOccurringProblem)
        val description: EditText = occurringProblemView.findViewById(R.id.descriptionOccurringProblem)
        val removeProblem: ImageView = occurringProblemView.findViewById(R.id.imageViewRemoveOccurringProblem)
        val visualizeProblem: ImageView = occurringProblemView.findViewById(R.id.imageViewVisualizeSetupOccurringProblem)
        
        val linearLayoutTouchable: LinearLayout = occurringProblemView.findViewById(R.id.linearLayoutTouchableOccurringProblem)
        val linearLayout: LinearLayout = occurringProblemView.findViewById(R.id.linearLayoutExpandableOccurringProblem)

        init {
            removeProblem.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val id = v?.id
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (id == R.id.imageViewRemoveOccurringProblem) {
                    listener.onProblemSolvedClick(
                        /*
                        Find the instance of DataProblem that has the same problem code
                        as the one clicked at the position given by the adapter
                         */
                        occurringProblemViewModel.filterListByProblemCode(
                            problemClicked.code
                        ).value?.get(position)!!,
                        v,
                        position
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_occurring_problem, parent, false
        )
        return ViewHolderOccurringProblem(view)
    }

    override fun getItemCount(): Int {
        return listOccurringProblem.value?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listOfItems = listOccurringProblem.value?.toMutableList()

        when(holder){
            is ViewHolderOccurringProblem ->{
                holder.apply {
                    setupCode.text = "Setup ${ listOfItems?.get(position)?.setupCode.toString() }"
                    description.setText(listOfItems?.get(position)?.description)
                    removeProblem.setImageResource(R.drawable.remove_setup_note_icon)
                    visualizeProblem.setImageResource(R.drawable.visibility_icon)

                    visualizeProblem.setOnClickListener {
                        Intent(it.context, DetailsSetupActivity::class.java).apply {
                            itemView.context.startActivity(this)
                        }
                    }

                    val expansion = listOfItems?.get(position)?.expansion
                    linearLayout.visibility = if (expansion!!) View.VISIBLE else View.GONE

                    linearLayoutTouchable.setOnClickListener{
                        listOfItems[position].expansion =
                            !listOfItems[position].expansion
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    /*
    Adds a new item to the list inside the ViewModel.
     */
    fun addItemToItemView(item: DataOccurringProblem){
        occurringProblemViewModel.addNewOccurringProblem(item)
        notifyDataSetChanged()
    }

    /*
    Used to set a new list to the recycler view
     */
    fun refreshList(problem: DataProblem) {
        listOccurringProblem.value =
            occurringProblemViewModel.filterListByProblemCode(problem.code).value
        notifyDataSetChanged()
    }

    /*
    Removes an item inside listOccurringProblem after checking
    if the item is contained in the same list
     */
    fun removeItemFromList(item: DataOccurringProblem){
        if (checkItemExist(item)) {
            occurringProblemViewModel.removeItemFromList(item)
            listOccurringProblem.value =
                occurringProblemViewModel.filterListByProblemCode(item.problemCode).value
            notifyDataSetChanged()
        }
    }
    /*
    Checks if an item is contained inside listOccurringProblem
     */
    private fun checkItemExist(item: DataOccurringProblem): Boolean{
        return occurringProblemViewModel.listOccurringProblem.value?.contains(item) !!
    }
}