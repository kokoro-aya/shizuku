package org.ironica.shizuku.playground.data

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Color

@Serializable
sealed class Tile

@Serializable
data class ColorfulTile(var color: Color): Tile()

@Serializable
data class MountainTile(var level: Int?): Tile() {
    init {
        if (level != null && level!! < 0)
            level = null
    }
}