package com.example.juegoemparejar

class Carta(val id: Int, val imageId: Int) {
    var isFlipped: Boolean = false

    fun flip() {
        isFlipped = !isFlipped
    }
}