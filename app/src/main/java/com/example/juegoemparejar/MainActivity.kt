package com.example.juegoemparejar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoemparejar.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private lateinit var buttonReset: Button
    private val flippedCards: MutableList<Carta> = mutableListOf()
    private var selectedCategory: MutableList<Carta> = cardsAnimales // Por defecto se mostraran las cartas de animales
    private lateinit var drawerManager: DrawerManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonReset = findViewById(R.id.buttonReset)

        cardsAnimales.shuffle()

        recyclerView = findViewById(R.id.contenedor)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        cardAdapter = CardAdapter(selectedCategory) { clickedCard ->
            onCardClick(clickedCard)
        }
        recyclerView.adapter = cardAdapter

        drawerManager = DrawerManager(this, recyclerView, cardAdapter, flippedCards)
    }


    private fun onCardClick(clickedCard: Carta) {
        if (!clickedCard.isFlipped && flippedCards.none { it.id + 100 == clickedCard.id } ||
            !clickedCard.isFlipped && flippedCards.none { it.id == clickedCard.id + 100 }) {
            clickedCard.flip()
            flippedCards.add(clickedCard)

            cardAdapter.notifyDataSetChanged()

            if (flippedCards.size == 2) {
                Handler(Looper.getMainLooper()).postDelayed({
                    checkForMatches()
                }, 500)
            }
        }
    }
    private fun ceckForWin(){
        if (selectedCategory.all { it.isFlipped }) {
            Toast.makeText(this, "Has ganado", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkForMatches() {
        if (flippedCards[0].id == flippedCards[1].id + 100 ||
            flippedCards[0].id + 100 == flippedCards[1].id
        ) {

            flippedCards.forEach { it.isMatched = true }
        } else {
            flippedCards.forEach { it.flip() }
        }

        flippedCards.clear()
        cardAdapter.notifyDataSetChanged()
        ceckForWin()
    }

}
