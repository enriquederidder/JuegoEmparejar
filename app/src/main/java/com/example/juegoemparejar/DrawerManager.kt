package com.example.juegoemparejar

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoemparejar.util.*
import com.google.android.material.navigation.NavigationView

class DrawerManager(
    private val activity: MainActivity,
    private val recyclerView: RecyclerView,
    private val cardAdapter: CardAdapter,
) {

    private val drawerLayout: DrawerLayout = activity.findViewById(R.id.drawer_layout)
    private val navigationView: NavigationView = activity.findViewById(R.id.navigation_view)

    init {
        setupDrawer()
    }

    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            activity.findViewById(R.id.toolbar),
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItem(menuItem.itemId)
        }
    }

    private fun handleNavigationItem(itemId: Int): Boolean {
        val selectedCategory = when (itemId) {
            R.id.itemAnimales -> cardsAnimales
            R.id.itemComida -> cardsComida
            R.id.itemPaises -> cardsPais
            else -> return false
        }

        activity.getCurrentCategory().clear()
        activity.getCurrentCategory().addAll(selectedCategory) // Set the current category

        selectedCategory.shuffle()
        cardAdapter.setNewData(selectedCategory)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
