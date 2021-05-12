package org.ironica.shizuku.bridge

import org.ironica.shizuku.playground.Tile

class YukiBridge(
    private val type: String,
    private val code: String,
    private var tiles: List<List<Tile>> = listOf(),
    grid: Array<Array<Block>>,

    tileInfo: TileInfo,
    itemInfo: ItemInfo,

)