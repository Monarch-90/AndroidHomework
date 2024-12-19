package com.example.helloworld4.view.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.helloworld4.databinding.FragmentRegistrationBinding
import com.example.helloworld4.view.ContainerActivity
import com.example.helloworld4.view_model.RegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val registrationViewModel: RegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReg.setOnClickListener {
            registration()
        }
    }

    private fun registration() {
        val login = binding.userLogin.text.toString().trim()
        val pass = binding.userPass.text.toString().trim()
        val passConfirm = binding.userPassConfirm.text.toString().trim()

        when {
            login.isEmpty() || pass.isEmpty() || passConfirm.isEmpty() -> {
                showMessage("Not Filled")
            }

            pass != passConfirm -> {
                showMessage("Passwords don't match")
            }

            !pass.matches(Regex("[a-zA-Z0-9]")) -> {
                showMessage("Incorrect password")
            }

            else -> {
                registrationViewModel.registerUser(login, pass, {
                    showMessage("User \"$login\" created")
                    (activity as ContainerActivity).navigateTo(AuthorizationFragment())
                }, { error ->
                    when (error) {
                        "\"$login\" already exists" -> showMessage("$login already exists")
                        else -> showMessage("Error: $error")
                    }
                })
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}