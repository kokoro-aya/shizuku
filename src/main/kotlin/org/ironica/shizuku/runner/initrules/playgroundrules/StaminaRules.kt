package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class StaminaRules(
    val initial: Int = 10000,
    val consumePerTurn: Int = 20,
    val inForest: Int = 50,
    val inDesert: Int = 50,
    val turnAround: Int = 120,
    val moveForward: Int = 100,
    val portalTeleport: Int = 240,
    val changeColor: Int = 0,
    val jump: Int = 150,
    val inLava: Int = 4500,
    val home: HomeStaminaRule = HomeStaminaRule(
        limit = 5, restore = 500, wait = 5, coolDown = 10,
    ),
    val beeper: BeeperStaminaRule = BeeperStaminaRule(
        take = 40, drop = 50, inBag = InBagRule(perItem = 10, perTurn = 5)
    ),
    val gem: GemStaminaRule = GemStaminaRule(
        take = 75, inBag = InBagRule(perItem = 15, perTurn = 5)
    ),
    val switch: SwitchStaminaRule = SwitchStaminaRule(open = 50, close = 35),
    val swim: SwimStaminaRule = SwimStaminaRule(
        jumpInto = 150, perTurn = 250, climbUp = 350
    ),
)

