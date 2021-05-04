package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class InBagRule(
    val perItem: Int, val perTurn: Int
)