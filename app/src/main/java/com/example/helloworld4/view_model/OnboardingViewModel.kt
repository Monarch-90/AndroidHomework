package com.example.helloworld4.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.helloworld4.Constants

class OnboardingViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences =
        application.getSharedPreferences("app-prefs", Application.MODE_PRIVATE)
    private val _isOnboardingCompleted = MutableLiveData<Boolean>()
    val isOnboardingCompleted: LiveData<Boolean>
        get() = _isOnboardingCompleted

    init {
        _isOnboardingCompleted.value =
            sharedPreferences.getBoolean(Constants.ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted() {
        sharedPreferences.edit().putBoolean(Constants.ONBOARDING_COMPLETED, true).apply()
        _isOnboardingCompleted.value = true
    }
}