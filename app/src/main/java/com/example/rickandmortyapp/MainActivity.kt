package com.example.rickandmortyapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.api.RetrofitInstance
import com.example.rickandmortyapp.model.Character
import com.example.rickandmortyapp.ui.theme.CharacterAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val characters = mutableListOf<Character>()
    private lateinit var adapter: CharacterAdapter

    private lateinit var recyclerCharacters: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerCharacters = findViewById(R.id.recyclerCharacters)
        swipe = findViewById(R.id.swipe)

        adapter = CharacterAdapter(characters)
        recyclerCharacters.layoutManager = LinearLayoutManager(this)
        recyclerCharacters.adapter = adapter

        swipe.setOnRefreshListener {
            fetchCharacters()
        }

        // Primera carga
        swipe.isRefreshing = true
        fetchCharacters()
    }

    private fun fetchCharacters(page: Int = 1) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getCharacters(page)
                }
                characters.clear()
                characters.addAll(response.results)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            } finally {
                swipe.isRefreshing = false
            }
        }
    }
}
