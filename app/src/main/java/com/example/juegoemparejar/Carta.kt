package com.example.juegoemparejar

class Carta(private val id: Int, val imageId: Int) {
    var isFlipped: Boolean = false

    fun flip() {
        isFlipped = !isFlipped
    }
}