package com.example.helloworld4.view.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld4.Constants
import com.example.helloworld4.data.model.Note
import com.example.helloworld4.databinding.FragmentTodolistBinding
import com.example.helloworld4.view.ContainerActivity
import com.example.helloworld4.view.notes.adapter.RvAdapter
import com.example.helloworld4.view_model.ImageViewModel
import com.example.helloworld4.view_model.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ToDoListFragment : Fragment() {
    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private var adapter: RvAdapter? = null
    private val toDoList = mutableListOf<Note>()
    private var imageViewModel: ImageViewModel? = null
    private val noteViewModel: NoteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodolistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewModelInitialization()
        setupRecyclerView()

        binding.btnAdd.setOnClickListener {
            newNote()
        }
    }

    private fun imageViewModelInitialization() {
        imageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]
    }

    private fun getCurrentUserId(): Long {
        val sharedPreferences =
            requireContext().getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(Constants.CURRENT_USER_ID, -1)
    }

    private fun setupRecyclerView() {
        adapter = RvAdapter(toDoList)
        binding.recView.adapter = adapter
        binding.recView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.getUserNotes(getCurrentUserId()).observe(viewLifecycleOwner) { notes ->
            adapter?.updateNotes(notes)
        }
    }

    private fun newNote() {
        (activity as ContainerActivity).navigateTo(NoteDetailFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}