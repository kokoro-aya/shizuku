package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Color

sealed class MiscInfo

data class ColorfulMiscInfo(var color: Color): MiscInfo()

data class MountainMiscInfo(var level: Int?): MiscInfo() {
    init {
        if (level != null && level!! < 0)
            level = null
    }
}