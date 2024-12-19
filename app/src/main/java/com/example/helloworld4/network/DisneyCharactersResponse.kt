package com.example.helloworld4.network

data class DisneyCharactersResponse(
    val data: List<Character>
)

data class Character(
    val id: String,
    val name: String,
    val imageUrl: String
)
