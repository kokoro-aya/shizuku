package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Lock(
    val coo: Coordinate,
    val controlled: MutableList<Coordinate>
    ) {
    @Transient var energy: Int = 0


}