package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class WinConditionRule(
    val hasGemMoreThan: Int,
    val hasGoldMoreThanCollected: Int,
    val hasGoldMoreThanSpent: Int,
    val noGemLeftOnGround: Boolean,
    val noGoldLeftOnGround: Boolean,
    val allSwitches: AllSwitchesCondition,
    val afterTurns: Int,
    val monsters: MonstersCondition,
    val satisfiedConditions: Int,
)

@Serializable
data class AllSwitchesCondition(
    val on: Boolean,
)

@Serializable
data class MonstersCondition(
    val allOrAnyKilled: Boolean,
    val beforeTurns: Int,
)