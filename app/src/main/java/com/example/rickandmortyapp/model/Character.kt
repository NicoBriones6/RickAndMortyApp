package com.example.rickandmortyapp.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val origin: Origin,
    val location: Location,
    val image: String
)
data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)