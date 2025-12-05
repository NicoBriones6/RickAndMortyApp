package com.example.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.Character
import com.example.rickandmortyapp.repository.CharacterRepository
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    private val repository = CharacterRepository()

    val characters = MutableLiveData<MutableList<Character>>(mutableListOf())
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingPage = false

    fun loadFirstPage() {
        currentPage = 1
        characters.value = mutableListOf()
        fetchCharacters()
    }

    fun fetchCharacters() {
        if (isLoadingPage || isLastPage) return

        viewModelScope.launch {
            try {
                isLoading.value = true
                isLoadingPage = true

                val response = repository.getCharacters(currentPage)

                // Si la API no devuelve más páginas
                if (response.results.isEmpty()) {
                    isLastPage = true
                    return@launch
                }

                // Agregar resultados nuevos
                val updatedList = characters.value ?: mutableListOf()
                updatedList.addAll(response.results)
                characters.value = updatedList

                currentPage++

            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage
            } finally {
                isLoading.value = false
                isLoadingPage = false
            }
        }
    }
}
