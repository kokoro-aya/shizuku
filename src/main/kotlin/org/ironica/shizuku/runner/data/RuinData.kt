package org.ironica.shizuku.runner.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.RuinType
import org.ironica.shizuku.playground.tile.Coordinate

@Serializable
data class RuinData(
    val coo: Coordinate,
    val type: RuinType
)
