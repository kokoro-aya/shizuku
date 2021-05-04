package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable

@Serializable
data class NotAllBeeperTakenCondition(
    val inBag: Boolean,
    val after: Int,
)