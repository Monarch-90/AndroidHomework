package com.example.helloworld4.ToDo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld4.Constants
import com.example.helloworld4.databinding.FragmentTodolistBinding

class ToDoListFragment : Fragment() {
    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RvAdapter
    private val toDoList = mutableListOf<Note>()

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

        val recView = binding.recView

        adapter = RvAdapter(toDoList)
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(requireContext())

            parentFragmentManager.setFragmentResultListener(Constants.DATA_SEND_KEY, this) { _, bundle ->
                    val title = bundle.getString(Constants.TITLE_KEY, "")
                    val text = bundle.getString(Constants.TEXT_KEY,"")
                    val date = bundle.getString(Constants.DATE_KEY, "")
                    val imageUri = bundle.getString(Constants.IMAGE_KEY)

                    val newNote = Note(title, text, date, imageUri)
                    toDoList.add(newNote)
                    adapter.updateNotes(toDoList)
            }

        binding.btnAdd.setOnClickListener {
            (activity as ContainerActivity).navigateTo(NoteDetailFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}