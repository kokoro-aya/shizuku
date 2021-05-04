package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Color

sealed class Item

object None: Item()
data class Switch(val on: Boolean): Item()
data class Gem(val disappearIn: Int): Item()
data class Beeper(val disappearIn: Int): Item()
@Serializable
data class Portal(val coo: Coordinate, val controlled: Array<Coordinate>, val color: Color = Color.WHITE)
@Serializable
data class Platform(val level: Int, var changes: Array<Pair<Int, Int>>? = null): Item()
