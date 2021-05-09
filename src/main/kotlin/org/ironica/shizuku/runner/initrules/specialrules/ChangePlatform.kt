package org.ironica.shizuku.runner.initrules.specialrules

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.tile.Coordinate

@Serializable
data class ChangePlatform(
    val coo: Coordinate,
    val inTurn: Int,
    val toLevel: Int,
)
