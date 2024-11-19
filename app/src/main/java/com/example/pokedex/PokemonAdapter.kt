package com.example.pokedex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class PokemonAdapter(context: Context, resource: Int, objects: ArrayList<Pokemon>) : ArrayAdapter<Pokemon>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var pokemon = getItem(position)
        var view = convertView?: LayoutInflater.from(context).inflate(R.layout.pokemon_row, parent, false)
        val pokemonName: TextView = view.findViewById(R.id.tvPokemonName)
        val pokemonSprite: ImageView = view.findViewById(R.id.ivPokemonSprite)

        pokemonName.text = pokemon?.name

        Glide.with(context)
            .load(pokemon?.sprite)
            .into(pokemonSprite)
        return view

    }

}