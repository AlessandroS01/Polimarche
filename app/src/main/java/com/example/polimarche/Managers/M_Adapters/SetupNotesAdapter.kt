package com.example.polimarche.Managers.M_Adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataSetupNotes

class SetupNotesAdapter(
    private val noteList: MutableList<DataSetupNotes>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderNotes(noteView : View) : RecyclerView.ViewHolder(noteView){
        var note: EditText = noteView.findViewById(R.id.editTextNotes)
        var add: ImageView = noteView.findViewById(R.id.imageViewAddNote)
        var remove: ImageView = noteView.findViewById(R.id.imageViewRemoveNote)

        init {
            /*
            Keeps updated the elements inside the noteList on user's changes
            of the edit Text.
             */
            note.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    noteList[adapterPosition].note = s.toString()
                    noteView.clearFocus()
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_setup_note, parent, false)
        return ViewHolderNotes(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderNotes ->{
                holder.apply {
                    note.setText(noteList[position].note)
                    add.setImageResource(R.drawable.create_note_setup_icon)
                    remove.setImageResource(R.drawable.remove_setup_note_icon)
                    /*
                    Creates a new note after the click on the imageView
                    that is linked to "add" attribute of the viewHolder.
                     */
                    add.setOnClickListener {
                        noteList.add(DataSetupNotes(""))
                        notifyItemInserted(noteList.size - 1)
                    }
                    /*
                    Removes the note after the click on the imageView
                    that is linked to "remove" attribute of the viewHolder.
                     */
                    remove.setOnClickListener {
                        if (itemCount != 1) {
                            noteList.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }
                }
            }
        }
    }
}