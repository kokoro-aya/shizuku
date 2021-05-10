package org.ironica.shizuku.playground

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(val x: Int, val y: Int) {
    fun toPairXY(): Pair<Int, Int> = Pair(x, y)
}
