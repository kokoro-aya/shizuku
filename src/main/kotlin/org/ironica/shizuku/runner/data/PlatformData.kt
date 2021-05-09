package org.ironica.shizuku.runner.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.tile.Coordinate

@Serializable
data class PlatformData(
    val coo: Coordinate,
    val level: Int,
)
