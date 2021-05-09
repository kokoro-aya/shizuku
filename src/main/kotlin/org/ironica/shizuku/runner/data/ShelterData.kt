package org.ironica.shizuku.runner.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.tile.Coordinate

@Serializable
data class ShelterData(
    val coo: Coordinate,
    val availableFor: Int,
)