package com.example.helloworld4.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.helloworld4.databinding.FragmentMenuBinding
import com.example.helloworld4.view.disney.fragments.DisneyCharactersFragment
import com.example.helloworld4.view.notes.container_activity.ContainerActivity
import com.example.helloworld4.view.notes.fragments.ToDoListFragment
import com.example.helloworld4.viewmodel.CharactersViewModel
import kotlin.getValue

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNotes.setOnClickListener {
            (activity as ContainerActivity).navigateTo(ToDoListFragment())
        }

        binding.btnDisney.setOnClickListener {
            (activity as ContainerActivity).navigateTo(DisneyCharactersFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}