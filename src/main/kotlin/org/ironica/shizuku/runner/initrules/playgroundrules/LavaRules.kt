package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class LavaRules(
    val canJumpInto: Boolean,
    val dieAfterTurns: Int?,
    val coolDown: Int?,
    val willDisappear: Boolean
)
