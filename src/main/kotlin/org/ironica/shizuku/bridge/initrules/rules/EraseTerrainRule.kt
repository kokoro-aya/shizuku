package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class EraseTerrainRule(
    val canErase: Boolean, val minLevelToErase: Int,
)
