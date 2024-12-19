package com.example.helloworld4.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.helloworld4.SplashFragment
import com.example.helloworld4.databinding.ActivityContainerBinding
import com.example.helloworld4.view_model.AuthorizationViewModel
import com.example.helloworld4.view_model.OnboardingViewModel

class ContainerActivity : AppCompatActivity() {
    private var binding: ActivityContainerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        Log.d("Debug", "ContainerActivity: onCreate called")
        start(savedInstanceState)
    }

    private fun start(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding?.fragmentContainer?.id ?: 0, SplashFragment())
                .commitAllowingStateLoss()
            Log.d("Debug", "ContainerActivity: SplashFragment started")
        }
    }

    fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding?.fragmentContainer?.id ?: 0, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
        Log.d("Debug", "ContainerActivity: Navigating to ${fragment.javaClass.simpleName}")
    }
}