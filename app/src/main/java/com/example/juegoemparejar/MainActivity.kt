package com.example.juegoemparejar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoemparejar.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val flippedCards: MutableList<Carta> = mutableListOf()
    var selectedCategory: MutableList<Carta> =
        cardsAnimales // Por defecto se mostraran las cartas de animales
    private lateinit var drawerManager: DrawerManager
    private var tries: Int = 15
    private lateinit var resetButton: Button
    private lateinit var textoVidas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardsAnimales.shuffle()

        recyclerView = findViewById(R.id.contenedor)
        resetButton = findViewById(R.id.buttonReset)
        textoVidas = findViewById(R.id.triesText)

        recyclerView.layoutManager = GridLayoutManager(this, 4)

        cardAdapter = CardAdapter(selectedCategory) { clickedCard ->
            onCardClick(clickedCard)
        }
        resetButton.setOnClickListener {
            resetGame()
        }

        recyclerView.adapter = cardAdapter
        drawerManager = DrawerManager(this, recyclerView, cardAdapter)

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

    private fun checkForMatches() {
        if (flippedCards[0].id == flippedCards[1].id + 100 ||
            flippedCards[0].id + 100 == flippedCards[1].id
        ) {

            flippedCards.forEach { it.isMatched = true }
        } else {
            flippedCards.forEach { it.flip() }
            tries--
        }

        flippedCards.clear()
        cardAdapter.notifyDataSetChanged()
        ceckForWin()
        checkForLose()
        setText()
    }

    fun resetGame() {
        selectedCategory.forEach { it.isFlipped = false; it.isMatched = false }
        tries = 15
        setText()
        selectedCategory.shuffle()
        cardAdapter.setNewData(selectedCategory)
    }

    private fun ceckForWin() {
        if (selectedCategory.all { it.isFlipped }) {
            Toast.makeText(this, "Has ganado", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkForLose() {
        if (tries == 0) {
            Toast.makeText(this, "Has perdido", Toast.LENGTH_LONG).show()
            resetGame()
        }
    }
    private fun setText() {
        textoVidas.text = tries.toString()
    }
}
