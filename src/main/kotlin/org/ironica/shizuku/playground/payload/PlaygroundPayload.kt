package org.ironica.shizuku.playground.payload

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.tile.Grid
import org.ironica.shizuku.playground.tile.Layout
import org.ironica.shizuku.playground.tile.MiscLayout
import org.ironica.shizuku.playground.tile.Portal

@Serializable
data class PlaygroundPayload(
    val grid: Grid,
    val layout: Layout,
    val misc: MiscLayout,
    val players: Array<SerializedPlayer>,
    val portals: Array<Portal>,
    val consoleLog: String,
    val special: String
)