package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class HomeStaminaRule(
    val limit: Int, val restore: Int, val coolDown: Int,
)