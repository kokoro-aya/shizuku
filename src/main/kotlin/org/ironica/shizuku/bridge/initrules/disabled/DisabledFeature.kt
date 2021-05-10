package org.ironica.shizuku.bridge.initrules.disabled

import kotlinx.serialization.Serializable

@Serializable
data class DisabledFeature(
    val type: String, val caller: String, val arg: Array<String>, val ret: String
)