package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class SwimRule(
    val canSwim: Boolean,
    val jumpFromLevelMax: Int,
    val climbUpToLevelMax: Int,
    val dieAfterTurns: Int,
)
