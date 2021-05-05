package org.ironica.shizuku.playground.data

import org.ironica.shizuku.playground.Block
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.characters.Player

data class GridObject(
    var block: Block,
    var layout: Item,
    var misc: MiscInfo,
    val stairs: MutableList<Direction>,
    val players: MutableList<Player>,
    var changes: List<Pair<Int, Block>>? = null,
)