package com.example.helloworld4.view.onboarding_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.helloworld4.databinding.FragmentSecondOnboardingBinding
import com.example.helloworld4.view.ContainerActivity

class SecondOnboardingFragment : Fragment() {
    private var _binding: FragmentSecondOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRightTwo.setOnClickListener {
            nextScreen()
        }
    }

    private fun nextScreen() {
        (activity as ContainerActivity).navigateTo(ThirdOnboardingFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}