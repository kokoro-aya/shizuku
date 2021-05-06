package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(var x: Int, var y: Int) {
    fun incrementX() { x += 1 }
    fun decrementX() { x -= 1 }
    fun incrementY() { y += 1 }
    fun decrementY() { y -= 1 }

    fun toPairXY(): Pair<Int, Int> = Pair(x, y)
}