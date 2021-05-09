package org.ironica.shizuku.runner.initrules.specialrules

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.tile.Coordinate
import org.ironica.shizuku.playground.Block

@Serializable
data class ChangeBlock(
    val coo: Coordinate,
    val inTurn: Int,
    val changeTo: Block = Block.OPEN,
)
