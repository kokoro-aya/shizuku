package org.ironica.shizuku.playground

import org.ironica.shizuku.bridge.Block
import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.items.*

data class Square(
    var tile: Tile,
    var blocked: MutableList<Direction> = mutableListOf(),
    var level: Int,
    var color: Color,
    val biome: Biome,
    var switch: Switch?,
    var gem: Gem?,
    var gold: Gold?,
    var portal: Portal?,
    var portion: Portion?,
    var platform: Platform?,
    val players: MutableList<AbstractCharacter> = mutableListOf(),
    var changes: MutableList<Pair<Int, Block>>? = null,
)
