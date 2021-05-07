package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.characters.Player

sealed class Item

object None: Item()
data class Switch(var on: Boolean): Item()
data class Gem(var disappearIn: Int): Item()
data class Beeper(var disappearIn: Int): Item()
@Serializable
data class Portal(
    val coo: Coordinate,
    val dest: Coordinate,
    val color: Color = Color.WHITE,
    var isActive: Boolean = true,
): Item() {
    @Transient var energy: Int = 0
}
data class Platform(var level: Int, var changes: List<Pair<Int, Int>>? = null): Item() {
    @Transient val players: MutableList<Player> = mutableListOf()
}
