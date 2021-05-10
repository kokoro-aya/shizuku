package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class MonsterRule(
    val rankUp: Array<Int>,
    val defeatBonus: DefeatBonusRule,
)

@Serializable
data class DefeatBonusRule(
    val gem: Array<Int>,
    val gold: Array<Int>,
    val stamina: Array<Int>,
)