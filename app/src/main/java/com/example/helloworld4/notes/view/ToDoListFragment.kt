package com.example.helloworld4.notes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld4.databinding.FragmentTodolistBinding
import com.example.helloworld4.notes.data.Note
import com.example.helloworld4.notes.intent.NoteIntent
import com.example.helloworld4.notes.model.RvAdapter
import com.example.helloworld4.notes.view_model.NoteViewModel

class ToDoListFragment : Fragment() {
    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RvAdapter
    private val toDoList = mutableListOf<Note>()
    private lateinit var noteViewModel: NoteViewModel

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
        val binding = FragmentTodolistBinding.bind(view)

        noteViewModel = ViewModelProvider(requireActivity())[NoteViewModel::class.java]

        adapter = RvAdapter(toDoList)
        binding.recView.adapter = adapter
        binding.recView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.updateNotes(state.notes)
        }

        binding.btnAdd.setOnClickListener {
            (activity as ContainerActivity).navigateTo(NoteDetailFragment())
        }

        noteViewModel.processIntent(NoteIntent.LoadNotes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}