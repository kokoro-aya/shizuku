package org.ironica.shizuku.playground.tile

import org.ironica.shizuku.playground.Biomes
import org.ironica.shizuku.playground.Block
import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.characters.Player

data class Tile(
    var layout: Layout,
    val blocked: MutableList<Direction>,
    var level: Int,
    var color: Color,
    var biome: Biomes,
    var switch: Switch?,
    var gem: Gem?,
    var beeper: Beeper?,
    var portal: Portal?,
    var platform: Platform?,
    val players: MutableList<Player>,
    var changes: List<Pair<Int, Block>>? = null,
)