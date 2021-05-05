package org.ironica.shizuku.playground.payload

import kotlinx.serialization.Serializable

@Serializable
data class SerializedPlatform(val x: Int, val y: Int, val level: Int)
