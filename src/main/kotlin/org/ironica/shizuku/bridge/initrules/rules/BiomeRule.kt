package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class BiomeRule(
    val normalCoef: Double,
    val hotCoef: Double,
    val coldCoef: Double,
    val veryHotCoef: Double,
    val veryColdCoef: Double,
)
