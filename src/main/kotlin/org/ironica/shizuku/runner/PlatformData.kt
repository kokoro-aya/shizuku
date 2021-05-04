package org.ironica.shizuku.runner

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.data.Coordinate

@Serializable
data class PlatformData(
    val coo: Coordinate,
    val level: Int,
)
