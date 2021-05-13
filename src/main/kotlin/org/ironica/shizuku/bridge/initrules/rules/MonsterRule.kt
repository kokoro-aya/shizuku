package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class MonsterRule(
    val rankUp: List<Int>,
    val atk: List<Int>,
    val stamina: List<Int>,
    val defeatBonus: DefeatBonusRule,
)

@Serializable
data class DefeatBonusRule(
    val gem: List<Int>,
    val gold: List<Int>,
    val stamina: List<Int>,
)