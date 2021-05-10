package org.ironica.shizuku.bridge.initrules

import kotlinx.serialization.Serializable
import org.ironica.shizuku.bridge.Block
import org.ironica.shizuku.playground.Coordinate

@Serializable
data class SpecialRules(
    val specialMessages: Array<SpecialMessage>,
    val changeBlocks: Array<ChangeBlock>,
    val changePlatforms: Array<ChangePlatform>,
    val randomInitGems: Int,
    val randomInitPortals: Int,
    val randomInitGolds: Int,
)

@Serializable
data class SpecialMessage(
    val msg: String,
    val inTurn: Int,
)

@Serializable
data class ChangeBlock(
    val coo: Coordinate,
    val inTurn: Int,
    val changeTo: Block,
)

@Serializable
data class ChangePlatform(
    val coo: Coordinate,
    val inTurn: Int,
    val toLevel: Int,
)