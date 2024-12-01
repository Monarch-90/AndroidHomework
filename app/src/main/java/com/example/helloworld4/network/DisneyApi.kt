package com.example.helloworld4.network

import retrofit2.Call
import retrofit2.http.GET

interface DisneyApi {
    @GET("character")
    fun getCharacters(): Call<DisneyCharactersResponse>
}
