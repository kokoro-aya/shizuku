package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(var x: Int, var y: Int) {
    fun toPairXY(): Pair<Int, Int> = Pair(x, y)
}