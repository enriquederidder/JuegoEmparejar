package com.example.juegoemparejar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.juegoemparejar.util.cards
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(){

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
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        // Setup the ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            findViewById(R.id.toolbar),
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Handle navigation item clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item1 -> {
                    // Handle item 1 click
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_item2 -> {
                    // Handle item 2 click
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                // Add more cases for other items
                else -> false
            }
        }
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


}
