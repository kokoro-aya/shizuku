package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Direction

@Serializable
data class Stair(val coo: Coordinate, val dir: Direction)