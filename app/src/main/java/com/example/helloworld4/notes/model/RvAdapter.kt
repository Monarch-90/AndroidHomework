package com.example.helloworld4.notes.model

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld4.Constants
import com.example.helloworld4.databinding.RvImageSampleBinding
import com.example.helloworld4.databinding.RvTextSampleBinding
import com.example.helloworld4.notes.data.Note

class RvAdapter(private var list: List<Note>) :
    RecyclerView.Adapter<RvAdapter.BaseViewHolder>() {

    abstract class BaseViewHolder(binding: View) : RecyclerView.ViewHolder(binding) {
        abstract fun bind(note: Note)
    }

    class TextNoteViewHolder(private val binding: RvTextSampleBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(note: Note) {
            binding.rvtTitle.text = note.title
            binding.rvtText.text = note.text
            binding.rvtDate.text = note.date
        }
    }

    class ImageNoteViewHolder(private val binding: RvImageSampleBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(note: Note) {
            binding.rviTitle.text = note.title
            binding.rviText.text = note.text
            binding.rviDate.text = note.date
            binding.ivImageIcon.setImageURI(Uri.parse(note.imageUri))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].imageUri == null) {
            Constants.TEXT_NOTE_KEY
        } else {
            Constants.IMAGE_NOTE_KEY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            Constants.TEXT_NOTE_KEY -> {
                val binding =
                    RvTextSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TextNoteViewHolder(binding)
            }

            Constants.IMAGE_NOTE_KEY -> {
                val binding =
                    RvImageSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageNoteViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val note = list[position]
        holder.bind(note)
    }

    fun updateNotes(newNotes: List<Note>) {
        list = newNotes
        notifyDataSetChanged()
    }
}