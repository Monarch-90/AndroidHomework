package com.example.helloworld4.view_model

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloworld4.Constants
import com.example.helloworld4.data.model.User
import com.example.helloworld4.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthorizationViewModel(
    private val userRepository: UserRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    fun loginUser(
        login: String,
        pass: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("Debug", "AuthorizationViewModel: loginUser function called with login: $login")
                val user = userRepository.getUser(login, pass)
                if (user != null) {
                    _currentUser.postValue(user)
                    saveLoginToSharedPreferences(login)
                    withContext(Dispatchers.Main) {
                        onSuccess(user)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("User: \"$login\" does not exist")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "Unknown error")
                }
            }
        }
    }

    private fun getSharedPreference(): SharedPreferences {
        return getApplication<Application>().getSharedPreferences(
            Constants.USER_PREFS,
            Context.MODE_PRIVATE
        )
    }

    private fun saveLoginToSharedPreferences(login: String) {
        val sharedPreferences = getSharedPreference()
        sharedPreferences.edit().putString(Constants.CURRENT_USER, login).apply()
    }

    fun getCurrentUserLogin(onResult: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Debug", "AuthorizationViewModel: getCurrentUserLogin function called")
            val sharedPreferences = getSharedPreference()
            val login = sharedPreferences.getString(Constants.CURRENT_USER, null)

            if (login != null) {
                val currentUserLogin = userRepository.currentUserLogin(login)
                Log.d("Debug", "AuthorizationViewModel: current user login is $currentUserLogin")
                withContext(Dispatchers.Main) {
                    onResult(currentUserLogin)
                }
            } else {
                withContext(Dispatchers.Main) {
                    onResult(null)
                }
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        Log.d("Debug", "AuthorizationViewModel: isUserLoggedIn called")
        val sharedPreferences = getSharedPreference()
        return sharedPreferences.getString(Constants.CURRENT_USER, null) != null
    }

    fun logout() {
        Log.d("Debug", "AuthorizationViewModel: logout function called")
        val sharedPreferences = getSharedPreference()
        sharedPreferences.edit().remove(Constants.CURRENT_USER).apply()
        _currentUser.postValue(null)
    }
}