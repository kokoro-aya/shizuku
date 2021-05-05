package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable

@Serializable
data class Lock(
    val coo: Coordinate,
    val controlled: Array<Coordinate>
    )