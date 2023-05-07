package com.example.polimarche.Users.Managers.Adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataSetup

class SetupNotesAdapter(
    private val newSetup: DataSetup
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
                    newSetup.notes[adapterPosition] = s.toString()
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
        return newSetup.notes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderNotes ->{
                holder.apply {
                    note.setText(newSetup.notes[position])
                    add.setImageResource(R.drawable.create_note_setup_icon)
                    remove.setImageResource(R.drawable.remove_general_small_icon)
                    /*
                    Creates a new note after the click on the imageView
                    that is linked to "add" attribute of the viewHolder.
                     */
                    add.setOnClickListener {
                        newSetup.notes.add("")
                        notifyItemInserted(newSetup.notes.size - 1)
                    }
                    /*
                    Removes the note after the click on the imageView
                    that is linked to "remove" attribute of the viewHolder.
                     */
                    remove.setOnClickListener {
                        if (itemCount != 1) {
                            println(position)
                            println(newSetup.notes.size)
                            newSetup.notes.removeAt(position)
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}