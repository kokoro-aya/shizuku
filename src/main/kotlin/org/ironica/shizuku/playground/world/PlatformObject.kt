package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.ItemEnum

data class PlatformObject(
    val content: ItemEnum = ItemEnum.PLATFORM,
    val level: Int
)
