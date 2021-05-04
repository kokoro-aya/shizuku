package org.ironica.shizuku.playground.payload

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.data.Grid
import org.ironica.shizuku.playground.data.Layout
import org.ironica.shizuku.playground.data.MiscLayout
import org.ironica.shizuku.playground.data.Portal

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