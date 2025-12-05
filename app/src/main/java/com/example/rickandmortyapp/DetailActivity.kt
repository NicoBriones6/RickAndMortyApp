package com.example.rickandmortyapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val img = findViewById<ImageView>(R.id.imgDetail)
        val name = findViewById<TextView>(R.id.tvDetailName)
        val species = findViewById<TextView>(R.id.tvDetailSpecies)
        val status = findViewById<TextView>(R.id.tvDetailStatus)
        val origin = findViewById<TextView>(R.id.tvDetailOrigin)
        val location = findViewById<TextView>(R.id.tvDetailLocation)

        // Recibir datos del intent
        val characterName = intent.getStringExtra("name")
        val characterSpecies = intent.getStringExtra("species")
        val characterStatus = intent.getStringExtra("status")
        val characterImage = intent.getStringExtra("image")
        val characterOrigin = intent.getStringExtra("origin")
        val characterLocation = intent.getStringExtra("location")

        name.text = characterName
        species.text = "Species: $characterSpecies"
        status.text = "Status: $characterStatus"
        origin.text = "Origin: $characterOrigin"
        location.text = "Location: $characterLocation"

        Glide.with(this)
            .load(characterImage)
            .centerCrop()
            .into(img)
    }
}
