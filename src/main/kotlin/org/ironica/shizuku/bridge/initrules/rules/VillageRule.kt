package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class VillageRule(
    val smallCoef: Double,
    val mediumCoef: Double,
    val largeCoef: Double,
)
