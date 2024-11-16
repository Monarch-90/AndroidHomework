package com.example.helloworld4.Lesson16.ToDo

import android.content.ContentValues
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld4.R

class RvAdapter(
    val list: List<Note>
) : RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<AppCompatTextView>(R.id.tv_title)
        val text = itemView.findViewById<AppCompatTextView>(R.id.tv_text)
        val date = itemView.findViewById<AppCompatTextView>(R.id.tv_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rec_view_item, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.text.text = list[position].text
        holder.date.text = list[position].date
    }
}