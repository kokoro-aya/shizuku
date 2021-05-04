package org.ironica.shizuku.playground.data

import org.ironica.shizuku.playground.Block
import org.ironica.shizuku.playground.Direction

data class BlockObject(
    var block: Block,
    var layout: Item,
    var misc: MiscInfo,
    val stairs: MutableList<Direction>,
    var changes: Array<Pair<Int, Block>>? = null
)