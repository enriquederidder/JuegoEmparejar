package com.example.juegoemparejar

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val cards = mutableListOf(
        Carta(2, R.drawable.bear_svgrepo_com),
        Carta(3, R.drawable.camel_svgrepo_com),
        Carta(4, R.drawable.bird_svgrepo_com),
        Carta(5, R.drawable.cat_with_wry_smile_svgrepo_com),
        Carta(6, R.drawable.crocodile_svgrepo_com),
        Carta(7, R.drawable.dolphin_svgrepo_com),
        Carta(8, R.drawable.dog_face_svgrepo_com),
        Carta(9, R.drawable.mouse_svgrepo_com),
    )

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Shuffle the cards
        cards.shuffle()

        // Duplicate the cards to ensure pairs
        cards.addAll(cards.toList())

        // Exclude the default poker card
        val filteredCards = cards.filterNot { it.imageId == R.drawable.poker_svgrepo_com }

        recyclerView = findViewById(R.id.contenedor)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        recyclerView.adapter = CardAdapter(filteredCards) { clickedCard ->
            // Handle card click event
            onCardClick(clickedCard)
        }
    }

    private fun onCardClick(clickedCard: Carta) {
        // Flip the clicked card
        clickedCard.flip()

        // Update the UI
        recyclerView.adapter?.notifyDataSetChanged()

        // Implement your logic to check for matches and game completion here
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucategorias, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
