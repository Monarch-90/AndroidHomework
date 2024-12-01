package com.example.helloworld4.network

data class DisneyCharactersResponse(
    val data: List<Character>
)

data class Character(
    val _id: String,
    val name: String,
    val imageUrl: String
)
