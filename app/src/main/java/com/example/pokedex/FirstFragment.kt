package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import com.example.pokedex.databinding.FragmentFirstBinding
import java.util.concurrent.Executors

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ListView capture on First Fragment
        val lvPokemon: ListView = view.findViewById(R.id.lvPokemon)
        refresh()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.action_refresh) {
            refresh()
            return true
        }

        if (id == R.id.action_settings) {
            var i = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(i)
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun refresh() {

        // Pokemon Array
        var pokemonArray:ArrayList<Pokemon> = ArrayList()

        // Adapter: Functions as a bridge between the ListView & pokemonArray/items
        val adapter = PokemonAdapter(
            requireContext(),
            R.layout.pokemon_row, // Layout for every Pokemon
            pokemonArray
        )

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {

            val api = PokemonAPI()
            pokemonArray = api.getAllPokemon()

            handler.post {
                adapter.clear()
                for (p: Pokemon in pokemonArray) {
                    adapter.add(p)
                }
            }

        }

        binding.lvPokemon.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}