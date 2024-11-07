package com.example.helloworld4.ToDo

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld4.Constants
import com.example.helloworld4.R

class RvAdapter(private var list: List<Note>) :
    RecyclerView.Adapter<RvAdapter.BaseViewHolder>() {

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(note: Note)
    }

    class TextNoteViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val title: AppCompatTextView = itemView.findViewById(R.id.tv_title)
        private val text: AppCompatTextView = itemView.findViewById(R.id.tv_text)
        private val date: AppCompatTextView = itemView.findViewById(R.id.tv_date)

        override fun bind(note: Note) {
            title.text = note.title
            text.text = note.text
            date.text = note.date
        }
    }

    class ImageNoteViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val title: AppCompatTextView = itemView.findViewById(R.id.tv_title)
        val text: AppCompatTextView = itemView.findViewById(R.id.tv_text)
        val date: AppCompatTextView = itemView.findViewById(R.id.tv_date)
        val imageView: AppCompatImageView = itemView.findViewById(R.id.iv_image_icon)

        override fun bind(note: Note) {
            title.text = note.title
            text.text = note.text
            date.text = note.date
            imageView.setImageURI(Uri.parse(note.imageUri))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].imageUri == null) {
            Constants.TEXT_NOTE
        } else {
            Constants.IMAGE_NOTE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            Constants.TEXT_NOTE -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_text_sample, parent, false)
                TextNoteViewHolder(view)
            }

            Constants.IMAGE_NOTE -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_image_sample, parent, false)
                ImageNoteViewHolder(view)
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