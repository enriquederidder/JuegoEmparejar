package com.example.juegoemparejar

class Carta(val id: Int, var imageId: Int) {
    var isFlipped: Boolean = false
    var isMatched: Boolean = false

    fun flip() {
        isFlipped = !isFlipped
    }
}