package com.example.rickandmortyapp.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.model.Character

class CharacterAdapter(
    private val list: MutableList<Character>,
    private val onItemClick: (Character) -> Unit) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(list[adapterPosition])
                }
            }
        }
        val img: ImageView = view.findViewById(R.id.imgCharacter)
        val name: TextView = view.findViewById(R.id.tvName)
        val species: TextView = view.findViewById(R.id.tvSpecies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = list[position]
        holder.name.text = character.name
        holder.species.text = "${character.species} • ${character.status}"
        Glide.with(holder.itemView)
            .load(character.image)
            .centerCrop()
            .into(holder.img)
    }

    fun updateData(newList: List<Character>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}