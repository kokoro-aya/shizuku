package org.ironica.shizuku.playground

sealed class Tile
object None: Tile()
object Open: Tile()
object Hill: Tile()
object Stone: Tile()
object Water: Tile()
data class Lava(var lastingFor: Int?): Tile()
object Tree: Tile()
object Ruin: Tile()
data class Shelter(val cap: Int): Tile()
data class Village(val size: Size): Tile()
data class Stair(val dir: Direction): Tile()
data class Lock(val controlled: MutableList<Coordinate>, var energy: Int): Tile()
data class Monster(
    var stamina: Int, val atk: Int, var rank: Int,
    var defeatGetStamina: Int,
    var defeatGetGem: Int,
    var defeatGetGold: Int,
): Tile()