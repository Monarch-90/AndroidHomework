package com.example.helloworld4.view.notes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld4.databinding.FragmentTodolistBinding
import com.example.helloworld4.data.model.Note
import com.example.helloworld4.intent.NoteIntent
import com.example.helloworld4.view.notes.adapter.RvAdapter
import com.example.helloworld4.view.notes.container_activity.ContainerActivity
import com.example.helloworld4.view_model.NoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ToDoListFragment : Fragment() {
    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RvAdapter
    private val toDoList = mutableListOf<Note>()
    private val noteViewModel: NoteViewModel by inject()

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

        adapter = RvAdapter(toDoList)
        binding.recView.adapter = adapter
        binding.recView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.updateNotes(state.notes)
        }

        binding.btnAdd.setOnClickListener {
            performTaskWithDelay()
        }

        noteViewModel.processIntent(NoteIntent.LoadNotes)
    }

    private fun showProgressBar() {
        binding.prBar.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.prBar.visibility = View.GONE
    }

    private suspend fun updateProgressBar() {
        val totalTime = 3000L
        val intervalTime = 100L
        val totalSteps = totalTime / intervalTime

        for(step in 1..totalSteps) {
            delay(intervalTime)
            val progress = (step.toFloat() / totalSteps * 100).toInt()
            binding.prBar.progress = progress
        }
    }

    private fun performTaskWithDelay() {
        showProgressBar()
        lifecycleScope.launch(Dispatchers.Main) {
            updateProgressBar()
            (activity as ContainerActivity).navigateTo(NoteDetailFragment())
            hideProgressbar()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}