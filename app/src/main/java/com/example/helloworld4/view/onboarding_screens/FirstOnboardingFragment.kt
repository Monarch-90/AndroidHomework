package com.example.helloworld4.view.onboarding_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.helloworld4.databinding.FragmentFirstOnboardingBinding
import com.example.helloworld4.view.ContainerActivity

class FirstOnboardingFragment : Fragment() {
    private var _binding: FragmentFirstOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRightOne.setOnClickListener {
            nextScreen()
        }
    }

    private fun nextScreen() {
        (activity as ContainerActivity).navigateTo(SecondOnboardingFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}