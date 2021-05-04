package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class SwitchStaminaRule(
    val open: Int, val close: Int
)