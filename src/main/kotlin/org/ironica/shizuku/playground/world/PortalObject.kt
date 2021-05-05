package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.ItemEnum
import org.ironica.shizuku.playground.data.Coordinate
import org.ironica.shizuku.playground.playground.Playground

data class PortalObject(
    val content: ItemEnum = ItemEnum.PORTAL,
    var isActive: Boolean = false,
) {
    lateinit var coo: Coordinate
}
