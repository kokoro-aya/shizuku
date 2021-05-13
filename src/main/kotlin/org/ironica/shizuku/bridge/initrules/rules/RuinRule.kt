package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class RuinRule(
    val gainStamina: Int,
    val loseStamina: Int,
    val getGem: Int,
    val loseGem: Int, // positive, to be deducted from gem count
    val getGold: Int,
    val loseGold: Int, // positive, to be deducte from gold count
)
