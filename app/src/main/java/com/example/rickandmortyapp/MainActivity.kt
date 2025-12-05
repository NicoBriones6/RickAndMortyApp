package com.example.rickandmortyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.model.Character
import com.example.rickandmortyapp.ui.theme.CharacterAdapter
import com.example.rickandmortyapp.viewmodel.CharacterViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : ComponentActivity() {

    private val viewModel: CharacterViewModel by viewModels()

    private lateinit var adapter: CharacterAdapter
    private lateinit var recyclerCharacters: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerCharacters = findViewById(R.id.recyclerCharacters)
        swipe = findViewById(R.id.swipe)

        adapter = CharacterAdapter(mutableListOf()) { character ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("name", character.name)
            intent.putExtra("species", character.species)
            intent.putExtra("status", character.status)
            intent.putExtra("image", character.image)
            intent.putExtra("origin", character.origin.name)
            intent.putExtra("location", character.location.name)
            startActivity(intent)
        }

        recyclerCharacters.layoutManager = LinearLayoutManager(this)
        recyclerCharacters.adapter = adapter

        viewModel.characters.observe(this) { list ->
            adapter.updateData(list)
        }

        recyclerCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { // Scrollea hacia abajo
                    val layoutManager = recyclerCharacters.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    val isAtBottom = (visibleItemCount + firstVisibleItemPosition) >= totalItemCount

                    if (isAtBottom) {
                        viewModel.fetchCharacters() // Cargar la siguiente página
                    }
                }
            }
        })


        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(this) { loading ->
            swipe.isRefreshing = loading
        }

        swipe.setOnRefreshListener {
            viewModel.fetchCharacters()
        }

        viewModel.fetchCharacters()
    }


}
