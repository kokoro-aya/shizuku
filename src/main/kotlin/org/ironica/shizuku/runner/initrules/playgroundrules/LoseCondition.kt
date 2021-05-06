package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class LoseCondition(
    val onePlayerKilled: Boolean,
    val oneSpecialistKilled: Boolean,
    val allPlayerKilled: Boolean,
    val allSpecialistKilled: Boolean,
    val allPlayerOrSpecialistKilled: Boolean,
    val notAllGemCollectedAfter: Int,
    val notAllSwitchToggledCondition: NotAllSwitchToggledCondition?,
    val notAllBeeperTakenCondition: NotAllBeeperTakenCondition?,
    val afterTurns: Int,
)