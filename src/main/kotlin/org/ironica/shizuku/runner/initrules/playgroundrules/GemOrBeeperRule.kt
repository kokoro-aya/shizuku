package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class GemOrBeeperRule(
    val disappearAfter: Int,
    val autoFail: Boolean,
)
