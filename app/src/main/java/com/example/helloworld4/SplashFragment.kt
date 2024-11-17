package com.example.helloworld4

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.helloworld4.databinding.FragmentSplashBinding
import com.example.helloworld4.notes.view.ContainerActivity
import com.example.helloworld4.notes.view.ToDoListFragment
import com.example.helloworld4.onboarding.FirstActivity
import com.example.helloworld4.registration_and_authorization.data.DatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        navigateAfterDelay()
    }

    private fun navigateAfterDelay() {
        lifecycleScope.launch(Dispatchers.Main) {
            updateProgressBar()
            (activity as ContainerActivity).navigateTo(ToDoListFragment())
        }
    }

    private suspend fun updateProgressBar() {
        val totalTime = 5000L
        val intervalTime = 100L
        val totalSteps = totalTime / intervalTime

        for (step in 1..totalSteps) {
            delay(intervalTime)
            val progress = (step.toFloat() / totalSteps * 100).toInt()
            binding.prBar.progress = progress
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
