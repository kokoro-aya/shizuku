package org.ironica.shizuku.runner.initrules.disabled

import kotlinx.serialization.Serializable

@Serializable
data class DisabledFeature(
    val type: String, val caller: String, val args: Array<String>, val ret: String
)