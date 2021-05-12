package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class RuinRule(
    val gainStamina: Int,
    val loseStamina: Int,
    val getGem: Int,
    val loseGem: Int,
    val getGold: Int,
    val loseGold: Int,
)
