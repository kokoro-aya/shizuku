package org.ironica.shizuku.bridge

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Coordinate

@Serializable
data class AdditionalGem(
    val coo: Coordinate,
    val appearIn: Int,
    val disappearIn: Int,
)

@Serializable
data class AdditionalGold(
    val coo: Coordinate,
    val appearIn: Int,
    val disappearIn: Int,
    val value: Int,
)