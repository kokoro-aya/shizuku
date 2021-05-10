package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class LoseConditionRule(
    val afterTurns: Int,
    val onePlayerKilled: Boolean,
    val oneSpecialistKilled: Boolean,
    val allPlayerKilled: Boolean,
    val allSpecialistKilled: Boolean,
    val allPlayerOrSpecialistKilled: Boolean,
    val gemsOnGroundAfter: Int,
    val switchesLeft: SwitchesLeftCondition,
    val goldLeft: GoldLeftCondition
)

@Serializable
data class SwitchesLeftCondition(
    val on: Boolean, val after: Int,
)

@Serializable
data class GoldLeftCondition(
    val inBag: Boolean, val after: Int,
)