package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class GemStaminaRule(
    val take: Int, val inBag: InBagRule
)