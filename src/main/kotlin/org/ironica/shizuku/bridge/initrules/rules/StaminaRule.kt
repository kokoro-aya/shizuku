package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class StaminaRule(
    val initial: Int,
    val consumePerTurn: Int,
    val inForest: Int,
    val onHill: Int,
    val onStone: Int,
    val inRuin: Int,
    val againstMonster: Int,
    val inShelter: Int,
    val inVillage: Int,
    val golds: GoldStaminaRule,
    val gems: GemStaminaRule,
    val portions: PortionStaminaRule,
    val switches: SwitchesStaminaRule,
    val turnAround: Int,
    val moveForward: Int,
    val portalTeleport: Int,
    val changeColor: Int,
    val turnLock: TurnLockOrStepStairStaminaRule,
    val jump: Int,
    val inLava: InLavaOrWaterStaminaRule,
    val inWater: InLavaOrWaterStaminaRule,
    val stair: TurnLockOrStepStairStaminaRule,
)

@Serializable
data class GoldStaminaRule(
    val take: Int,
    val drop: Int,
    val inBag: InBagStaminaRule,
)

@Serializable
data class GemStaminaRule(
    val take: Int,
    val inBag: InBagStaminaRule,
)

@Serializable
data class InBagStaminaRule(
    val perItem: Int,
    val perTurn: Int,
)

@Serializable
data class PortionStaminaRule(
    val small: Int, val medium: Int, val large: Int,
)

@Serializable
data class SwitchesStaminaRule(
    val open: Int, val close: Int,
)

@Serializable
data class TurnLockOrStepStairStaminaRule(
    val up: Int, val down: Int,
)

@Serializable
data class InLavaOrWaterStaminaRule(
    val jumpInto: Int, val perTurn: Int, val climbUp: Int,
)