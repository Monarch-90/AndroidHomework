package com.example.helloworld4.view.onboarding_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.helloworld4.databinding.FragmentThirdOnboardingBinding
import com.example.helloworld4.view.ContainerActivity
import com.example.helloworld4.view.menu.MenuFragment
import com.example.helloworld4.view.users.AuthorizationFragment
import com.example.helloworld4.view_model.AuthorizationViewModel
import com.example.helloworld4.view_model.OnboardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThirdOnboardingFragment : Fragment() {
    private var _binding: FragmentThirdOnboardingBinding? = null
    private val binding get() = _binding!!
    private val onboardingViewModel: OnboardingViewModel by viewModel()
    private val authorizationViewModel: AuthorizationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRightThree.setOnClickListener {
            onboardingViewModel.setOnboardingCompleted()
            nextScreen()
        }
    }

    private fun nextScreen() {
        if (authorizationViewModel.isUserLoggedIn()) {
            (activity as ContainerActivity).navigateTo(MenuFragment())
        } else {
            (activity as ContainerActivity).navigateTo(AuthorizationFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}