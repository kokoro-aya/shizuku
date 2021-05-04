package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class SwimStaminaRule(
    val jumpInto: Int, val perTurn: Int, val climbUp: Int
)