package com.example.polimarche.Users.All.Adapters

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataSolvedProblem

class SolvedProblemAdapter(
    private val listSolvedProblem: MutableList<DataSolvedProblem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderSolvedProblem(solvedProblemView : View) : RecyclerView.ViewHolder(solvedProblemView){
        val setupCode: TextView = solvedProblemView.findViewById(R.id.setupCodeSolvedProblem)
        val description: EditText = solvedProblemView.findViewById(R.id.descriptionSolvedProblem)
        val reappearedProblem: ImageView = solvedProblemView.findViewById(R.id.imageViewReappearedSolvedProblem)

        val linearLayoutTouchable: LinearLayout = solvedProblemView.findViewById(R.id.linearLayoutTouchableSolvedProblem)
        val linearLayout: LinearLayout = solvedProblemView.findViewById(R.id.linearLayoutExpandableSolvedProblem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_solved_problem, parent, false
        )
        return ViewHolderSolvedProblem(view)
    }

    override fun getItemCount(): Int {
        return listSolvedProblem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderSolvedProblem ->{
                holder.apply {
                    setupCode.text = listSolvedProblem[position].setupCode.toString()
                    description.setText(listSolvedProblem[position].description)
                    reappearedProblem.setImageResource(R.drawable.remove_setup_note_icon)

                    val expansion = listSolvedProblem[position].expansion
                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    linearLayoutTouchable.setOnClickListener{
                        listSolvedProblem[position].expansion = !listSolvedProblem[position].expansion
                        notifyItemChanged(position)
                    }
                    /*
                    If the users click on the imageVew, a dialog box pops up and, inside
                    the element, the user can put a description of the problem again.
                    Then if the user confirms the reappearance the element will be removed.
                     */
                    reappearedProblem.setOnClickListener {
                        showDialog(holder.itemView, position)
                    }
                }
            }
        }
    }

    /*
    Create a dialog box that lets the user add a description
    if the he clicks on reappeared problem
     */
    private fun showDialog(view: View, position: Int) {
        val dialog = Dialog(view.context)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_problem_reappeared_or_solved)

        val newDescription = dialog.findViewById(R.id.editTextReappearedProblem) as EditText

        val confirmReappearedProblem = dialog.findViewById(R.id.confirmReappearedProblem) as FrameLayout
        val cancelReappearedProblem = dialog.findViewById(R.id.cancelReappearedProblem) as FrameLayout

        /*
        Confirm that the problem that was solved before reappeared on the same setup.
         */
        confirmReappearedProblem.setOnClickListener {
            listSolvedProblem.removeAt(position)
            notifyDataSetChanged()
            dialog.dismiss()
        }
        cancelReappearedProblem.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}