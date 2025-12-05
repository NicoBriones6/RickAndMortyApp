package com.example.rickandmortyapp.api

import com.example.rickandmortyapp.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1
    ): CharactersResponse
}
