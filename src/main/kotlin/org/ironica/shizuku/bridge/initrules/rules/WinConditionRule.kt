package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class WinConditionRule(
    val hasGemMoreThan: Int,
    val hasGoldMoreThanCollected: Int,
    val hasGoldMoreThanSpent: Int,
    val noGemLeftOnGround: Boolean,
    val noGoldLeftOnGround: Boolean,
    val allSwitches: Int, // 0 -> No win condition, 1 -> All switch is OFF, 2 -> All switch is ON
    val afterTurns: Int,
    val monsters: MonstersCondition,
    val satisfiedConditions: Int,
)

@Serializable
data class MonstersCondition(
    val allOrAnyKilled: Boolean,
    val beforeTurns: Int,
)