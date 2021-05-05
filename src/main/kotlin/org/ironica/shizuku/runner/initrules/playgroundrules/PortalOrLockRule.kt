package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class PortalOrLockRule(
    val defaultEnergy: Int,
    val decreaseEachUsage: Int,
)
