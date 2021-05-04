package org.ironica.shizuku.runner.initrules.specialrules

import kotlinx.serialization.Serializable

@Serializable
data class SpecialMessage(
    val msg: String,
    val inTurn: Int,
)
