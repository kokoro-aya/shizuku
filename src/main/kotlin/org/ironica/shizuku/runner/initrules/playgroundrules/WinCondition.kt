package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class WinCondition(
    val allGemCollected: Boolean,
    val allSwitch: AllSwitchCondition,
    val allBeepersInBag: Boolean,
    val afterTurns: Int,
    val satisfiedCondition: Int,
)