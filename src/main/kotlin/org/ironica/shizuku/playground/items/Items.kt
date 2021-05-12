package org.ironica.shizuku.playground.items

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Coordinate
import org.ironica.shizuku.playground.Size
import org.ironica.shizuku.playground.characters.AbstractCharacter

sealed class Item
data class Switch(var on: Boolean): Item()
data class Gem(val disappearIn: Int): Item()
data class Gold(val value: Int, val disappearIn: Int): Item()
data class Portion(val size: Size, val disappearIn: Int): Item()
data class Platform(
    var level: Int,
    var changes: MutableList<Pair<Int, Int>>? = null,
    val players: MutableList<AbstractCharacter> = mutableListOf(),
): Item()
data class Portal(
    val coo: Coordinate,
    val dest: Coordinate,
    val color: Color,
    var isActive: Boolean,
    var energy: Int
): Item()