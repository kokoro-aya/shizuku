package org.ironica.shizuku.runner

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.data.Coordinate

@Serializable
data class GemOrBeeper(
    val coo: Coordinate,
    val appearIn: Int,
)
