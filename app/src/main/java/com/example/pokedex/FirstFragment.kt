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
import android.widget.ListView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.pokedex.databinding.FragmentFirstBinding
import java.util.concurrent.Executors


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var pokemonArray:ArrayList<Pokemon> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lvPokemon: ListView = view.findViewById(R.id.lvPokemon)
        refresh()

        val adapter = PokemonAdapter (
            requireContext(),
            R.layout.pokemon_row,
            pokemonArray
        )

        lvPokemon.setOnItemClickListener{adapter, _, position, _ ->
            val pokemon = adapter.getItemAtPosition(position) as Pokemon
            val args = Bundle().apply {
                putSerializable("item", pokemon)
            }
            NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_pokemonDetails, args)
        }

        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_refresh -> {
                refresh()
                Toast.makeText(requireContext(), "Pokemon Loaded Successfully", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun refresh() {

        val adapter = PokemonAdapter(
            requireContext(),
            R.layout.pokemon_row,
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