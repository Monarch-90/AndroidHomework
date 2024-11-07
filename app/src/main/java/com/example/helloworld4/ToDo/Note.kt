package com.example.helloworld4.ToDo

import java.io.Serializable

data class Note(
    val title: String,
    val text: String,
    val date: String,
    val imageUri: String? = null
) : Serializable