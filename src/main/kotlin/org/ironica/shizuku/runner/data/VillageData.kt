package org.ironica.shizuku.runner.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Size
import org.ironica.shizuku.playground.tile.Coordinate

@Serializable
data class VillageData(
    val coo: Coordinate,
    val size: Size,
)
