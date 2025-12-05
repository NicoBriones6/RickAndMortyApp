package com.example.rickandmortyapp.model

data class CharactersResponse(
    val info: Info,
    val results: List<Character>
)