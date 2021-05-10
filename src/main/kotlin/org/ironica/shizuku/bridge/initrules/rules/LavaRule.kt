package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class LavaRule(
    val canJumpInto: Boolean,
    val dieAfterTurns: Int,
    val coolDown: Int,
    val willDisappear: Int,
)
