package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class PortalOrLockRule(
    val defaultEnergy: Int,
    val decreaseEachUsage: Int,
)
