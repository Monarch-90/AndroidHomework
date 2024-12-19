package com.example.helloworld4.view.users

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.helloworld4.Constants
import com.example.helloworld4.databinding.FragmentAuthorizationBinding
import com.example.helloworld4.view.ContainerActivity
import com.example.helloworld4.view.menu.MenuFragment
import com.example.helloworld4.view_model.AuthorizationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private val authorizationViewModel: AuthorizationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnValidation()

        binding.buttonAuth.setOnClickListener {
            authorization()
        }

        binding.buttonGoReg.setOnClickListener {
            goToRegistration()
        }
    }

    private fun goToRegistration() {
        (activity as ContainerActivity).navigateTo(RegistrationFragment())
    }

    private fun authorization() {
        val login = binding.loginAuth.text.toString().trim()
        val pass = binding.passAuth.text.toString().trim()

        if (login.isNotEmpty() && pass.isNotEmpty()) {
            authorizationViewModel.loginUser(login, pass, { user ->
                val sharedPreferences = requireContext().getSharedPreferences(
                    Constants.USER_PREFS,
                    Context.MODE_PRIVATE
                )
                with(sharedPreferences.edit()) {
                    putLong(Constants.CURRENT_USER_ID, user.id)
                    apply()
                }
                showMessage("Successfully")
                (activity as ContainerActivity).navigateTo(MenuFragment())
            }, { error ->
                showMessage("Error: $error")
            })
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun btnValidation() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buttonAuth.isEnabled = binding.loginAuth.text?.isNotEmpty() == true &&
                        binding.passAuth.text?.isNotEmpty() == true
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        binding.loginAuth.addTextChangedListener(textWatcher)
        binding.passAuth.addTextChangedListener(textWatcher)
        binding.buttonAuth.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}