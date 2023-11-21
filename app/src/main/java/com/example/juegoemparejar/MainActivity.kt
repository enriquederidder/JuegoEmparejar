package com.example.juegoemparejar

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper
import com.example.juegoemparejar.util.cards

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val flippedCards: MutableList<Carta> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cards.shuffle()
        val filteredCards = cards

        recyclerView = findViewById(R.id.contenedor)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        cardAdapter = CardAdapter(filteredCards) { clickedCard ->
            onCardClick(clickedCard)
        }
        recyclerView.adapter = cardAdapter
    }

    private fun onCardClick(clickedCard: Carta) {
        if (!clickedCard.isFlipped && flippedCards.none { it.id+100 == clickedCard.id }
            || !clickedCard.isFlipped && flippedCards.none { it.id == clickedCard.id+100}) {
            clickedCard.flip()
            flippedCards.add(clickedCard)

            cardAdapter.notifyDataSetChanged()

            if (flippedCards.size == 2) {
                Handler(Looper.getMainLooper()).postDelayed({
                    checkForMatches()
                }, 700)
            }
        }
    }

    private fun checkForMatches() {
        if (flippedCards[0].id == flippedCards[1].id + 100
            || flippedCards[0].id + 100 == flippedCards[1].id ) {
            flippedCards.forEach { it.isMatched = true }
        } else {
            flippedCards.forEach { it.flip() }
        }

        flippedCards.clear()
        cardAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucategorias, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
