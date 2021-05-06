package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class SwimRules(
    val canSwim: Boolean,
    val jumpFromLevelMax: Int,
    val climbUpToLevelMax: Int,
    val dieAfterTurns: Int?,
)
