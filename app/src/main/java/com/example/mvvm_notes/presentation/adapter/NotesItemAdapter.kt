package com.example.mvvm_notes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_notes.databinding.NoteItemBinding
import com.example.mvvm_notes.domain.model.Note
import com.example.mvvm_notes.util.ColorProvider

class NotesItemAdapter(private val clickListener: (noteId: Long) -> Unit) : ListAdapter<Note, NotesItemAdapter.NoteItemViewHolder>(
    NoteItemCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder =
        NoteItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val item = getItem(position)
        val cardView = holder.binding.root as CardView
        holder.bind(item, clickListener)
        cardView.setCardBackgroundColor(ColorProvider.getColorResourceId(position))
    }

    class NoteItemViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup) : NoteItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return NoteItemViewHolder(binding)
            }
        }

        fun bind(item: Note, clickListener: (noteId: Long) -> Unit) {
            binding.note = item
            binding.root.setOnClickListener { clickListener(item.id) }
        }
    }

    class NoteItemCallback: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }

}
