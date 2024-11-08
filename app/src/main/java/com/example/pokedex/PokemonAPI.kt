package com.example.pokedex

import android.net.Uri
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PokemonAPI {

    val baseUrl = "https://pokeapi.co/api/v2/pokemon"

    fun getAllPokemon():ArrayList<Pokemon> {

        val buildUri = Uri.parse(baseUrl)
            .buildUpon()
            .build()

        val url = buildUri.toString()
        return doCall(url)

    }

    fun doCall(url:String): ArrayList<Pokemon> {

        try {
            val jsonResponse:String = HttpUtils.get(url)
            return processJson(jsonResponse)
        } catch (e: IOException) {
            println("Failed: doCall")
        }
        return ArrayList();
    }

    private fun processJson(jsonResponse:String): ArrayList<Pokemon> {

        var pokemons:ArrayList<Pokemon> = ArrayList<Pokemon>()

        try {

            var data: JSONObject = JSONObject(jsonResponse)
            var jsonPokemons: JSONArray = data .getJSONArray("results")
            for (i in 0 until jsonPokemons.length()) {

                var jsonPokemon:JSONObject = jsonPokemons.getJSONObject(i)
                var pokemon:Pokemon = Pokemon()

                pokemon.name = jsonPokemon.getString("name")

                pokemons.add(pokemon)

            }

        } catch (e: JSONException) {
            println("failed: processJson")
        }
        return pokemons
    }

}