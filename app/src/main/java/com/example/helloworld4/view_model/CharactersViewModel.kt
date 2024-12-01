package com.example.helloworld4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helloworld4.network.Character
import com.example.helloworld4.network.DisneyApi
import com.example.helloworld4.network.DisneyCharactersResponse
import com.example.helloworld4.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersViewModel : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val disneyApi = RetrofitClient.instance.create(DisneyApi::class.java)

    fun loadCharacters() {
        disneyApi.getCharacters().enqueue(object : Callback<DisneyCharactersResponse> {
            override fun onResponse(
                call: Call<DisneyCharactersResponse>,
                response: Response<DisneyCharactersResponse>
            ) {
                if (response.isSuccessful) {
                    _characters.value = response.body()?.data
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<DisneyCharactersResponse>, t: Throwable) {
                _error.value = "Failure: ${t.message}"
            }
        })
    }
}
