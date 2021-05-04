package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class EraseTerrainCondition(
    val canErase: Boolean,
    val minLevelToErase: Int,
)