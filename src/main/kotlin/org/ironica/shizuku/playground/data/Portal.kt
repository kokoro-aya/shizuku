package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Color

@Serializable
data class Portal(val coo: Coordinate, val controlled: Array<Coordinate>, val color: Color = Color.WHITE)