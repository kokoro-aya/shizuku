package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.ItemEnum
import org.ironica.shizuku.playground.tile.Portal

data class PortalObject(
    val content: ItemEnum = ItemEnum.PORTAL,
    val initActive: Boolean = false,
    val color: Color = Color.WHITE,
) {
    lateinit var portal: Portal
}
