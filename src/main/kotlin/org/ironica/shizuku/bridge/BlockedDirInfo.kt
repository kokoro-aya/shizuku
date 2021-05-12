package org.ironica.shizuku.bridge

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Coordinate
import org.ironica.shizuku.playground.Direction

@Serializable
data class BlockedDirInfo(
    val coo: Coordinate,
    val blocked: List<Direction>
)
