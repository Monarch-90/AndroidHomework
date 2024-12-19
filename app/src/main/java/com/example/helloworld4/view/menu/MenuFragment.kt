package com.example.helloworld4.view.menu

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.helloworld4.databinding.FragmentMenuBinding
import com.example.helloworld4.view.ContainerActivity
import com.example.helloworld4.view.disney.fragments.DisneyCharactersFragment
import com.example.helloworld4.view.notes.fragments.ToDoListFragment
import com.example.helloworld4.view.users.AuthorizationFragment
import com.example.helloworld4.view_model.AuthorizationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private var requestPermissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private val authorizationViewModel: AuthorizationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentUserLogin()
        registerImage()

        binding.btnLogOut.setOnClickListener {
            logout()
        }

        binding.btnNotes.setOnClickListener {
            goToScreenAfterCheckPermission()
        }

        binding.btnDisney.setOnClickListener {
            navigateToDisneyCharactersScreen()
        }
    }

    private fun logout() {
        authorizationViewModel.logout()
        (activity as ContainerActivity).navigateTo(AuthorizationFragment())
    }

    private fun checkPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher?.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    private fun registerImage() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true &&
                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true &&
                permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
            ) {
                navigateToToDoListScreen()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permissions are required to view notes with images",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun goToScreenAfterCheckPermission() {
        if (checkPermissions()) {
            navigateToToDoListScreen()
        } else {
            requestPermissions()
        }
    }

    private fun navigateToToDoListScreen() {
        (activity as ContainerActivity).navigateTo(ToDoListFragment())
    }

    private fun navigateToDisneyCharactersScreen() {
        (activity as ContainerActivity).navigateTo(DisneyCharactersFragment())
    }

    private fun getCurrentUserLogin() {
        authorizationViewModel.getCurrentUserLogin { login ->
            login?.let {
                binding.tvLogin.text = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}