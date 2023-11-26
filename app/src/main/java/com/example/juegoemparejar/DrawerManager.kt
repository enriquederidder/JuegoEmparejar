package com.example.juegoemparejar

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoemparejar.util.*
import com.google.android.material.navigation.NavigationView
/**
 * Gestiona la configuración y las interacciones del navigation drawer en MainActivity.
 *
 * @property activity Instancia de la actividad principal.
 * @property recyclerView El RecyclerView utilizado para mostrar las cartas.
 * @property cardAdapter El adaptador para gestionar la visualización de las cartas en el RecyclerView.
 */
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
            activity.findViewById(R.id.toolbar),// opcional
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItem(menuItem.itemId)
        }
    }

    /**
     * Maneja la selección de elementos en el menú de navegación.
     *
     * @param itemId El ID del elemento del menú seleccionado.
     * @return True si el evento de selección fue manejado con éxito, false en caso contrario.
     */    private fun handleNavigationItem(itemId: Int): Boolean {
        val selectedCategory = when (itemId) {
            R.id.itemAnimales -> cardsAnimales
            R.id.itemComida -> cardsComida
            R.id.itemPaises -> cardsPais
            R.id.itemRest -> {
                activity.resetGame()
                // Ciera el drawer al acer en click en el elemento, a mi me gusta asi.
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }

            else -> return false
        }
        // Actualiza la lista de cartas seleccionadas y resetea el juego
        activity.selectedCategory = selectedCategory
        activity.resetGame()
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
