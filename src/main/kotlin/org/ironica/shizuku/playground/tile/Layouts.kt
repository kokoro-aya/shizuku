package org.ironica.shizuku.playground.tile

import org.ironica.shizuku.playground.RuinType
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.Size

sealed class Layout
object None: Layout()
object Open: Layout()
object Hill: Layout()
object Stone: Layout()
object Water: Layout()
data class Lava(val existsFor: Int): Layout()
object Tree: Layout()
data class Ruin(
    val type: RuinType
): Layout()
data class Shelter(
    var availableFor: Int,
): Layout()
data class Village(
    val size: Size,
): Layout()
data class Stair(
    val dir: Direction
): Layout()
data class Lock(
    val controlled: MutableList<Coordinate>,
    var energy: Int = 0,
): Layout()