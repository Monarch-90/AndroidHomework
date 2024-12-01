package com.example.helloworld4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.helloworld4.databinding.FragmentSplashBinding
import com.example.helloworld4.view.menu.MenuFragment
import com.example.helloworld4.view.notes.container_activity.ContainerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

        navigateAfterDelay()
    }

    private fun navigateAfterDelay() {
        lifecycleScope.launch(Dispatchers.Main) {
            updateProgressBar()
            (activity as ContainerActivity).navigateTo(MenuFragment())
        }
    }

    private suspend fun updateProgressBar() {
        val totalTime = 1000L
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
