package com.example.juegoemparejar

import android.media.Image
import java.util.Locale.Category
import kotlin.math.E

class Carta {
    private companion object{
        val imagenesCartas = arrayOf(
            R.drawable.poker_svgrepo_com
        )
    }

    constructor(idCarta: Int, estado: Estado) {
        this.idCarta = idCarta
        this.estado = estado
    }

    private var idCarta: Int = -1
    private var estado: Estado = Estado.HIDDEN
    private var imagenCarta: R.drawable.po

}