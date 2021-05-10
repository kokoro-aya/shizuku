package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class GemOrGoldRule(
    val autoFail: Boolean,
)
