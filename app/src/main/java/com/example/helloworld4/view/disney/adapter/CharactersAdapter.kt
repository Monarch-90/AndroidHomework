package com.example.helloworld4.view.disney.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helloworld4.databinding.DisneySampleBinding
import com.example.helloworld4.network.Character

class CharactersAdapter(private val characters: List<Character>) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            DisneySampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size

    class CharacterViewHolder(private val binding: DisneySampleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.tvDisneyItem.text = character.name
            Glide.with(binding.ivDisneyItem.context).load(character.imageUrl)
                .into(binding.ivDisneyItem)
        }
    }
}
