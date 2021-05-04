package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class NotAllSwitchToggledCondition(
    val on: Boolean,
    val after: Int,
)