package com.example.rickandmortyapp.repository

import com.example.rickandmortyapp.api.RetrofitInstance

class CharacterRepository {

    suspend fun getCharacters(page: Int = 1) =
        RetrofitInstance.api.getCharacters(page)
}
