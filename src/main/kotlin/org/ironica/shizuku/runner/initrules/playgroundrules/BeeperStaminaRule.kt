package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class BeeperStaminaRule(
    val take: Int, val drop: Int, val inBag: InBagRule
)