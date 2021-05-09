package org.ironica.shizuku.runner

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.tile.Coordinate

@Serializable
data class GemOrBeeper(
    val coo: Coordinate,
    var appearIn: Int, // a negative value indicates that it will appear after the last is consumed
    val disappearIn: Int = 0,
)
