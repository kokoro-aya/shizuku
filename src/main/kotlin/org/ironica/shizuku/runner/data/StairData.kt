package org.ironica.shizuku.runner.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.tile.Coordinate

@Serializable
data class StairData(val coo: Coordinate, val dir: Direction)