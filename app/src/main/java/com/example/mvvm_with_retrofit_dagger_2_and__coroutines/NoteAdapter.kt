package com.example.mvvm_with_retrofit_dagger_2_and__coroutines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.databinding.NoteItemBinding
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteResponse
//(parameter name : parameter type) -> return type
class NoteAdapter(private val onNoteClicked: (NoteResponse) -> Unit) : ListAdapter<NoteResponse, NoteAdapter.NoteViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }


    inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            //viewbinding code for recyclerview
        fun bind(note: NoteResponse) {
            binding.title.text = note.title
            binding.desc.text = note.description
                binding.root.setOnClickListener{
                    onNoteClicked(note)
                }
        }



    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<NoteResponse>() {
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }

    }
}