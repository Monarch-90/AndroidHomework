package com.example.helloworld4.view.disney.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld4.databinding.FragmentDisneyCharactersBinding
import com.example.helloworld4.viewmodel.CharactersViewModel
import com.example.helloworld4.R
import com.example.helloworld4.view.disney.adapter.CharactersAdapter

class DisneyCharactersFragment : Fragment(R.layout.fragment_disney_characters) {
    private val charactersViewModel: CharactersViewModel by viewModels()
    private var _binding: FragmentDisneyCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisneyCharactersBinding.inflate(inflater, container, false).apply {
            viewModel = charactersViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvDisney.layoutManager = LinearLayoutManager(requireContext())

        charactersViewModel.characters.observe(viewLifecycleOwner, Observer { characters ->
            adapter = CharactersAdapter(characters)
            binding.rvDisney.adapter = adapter
        })

        charactersViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })

        charactersViewModel.loadCharacters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}