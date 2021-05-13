package org.ironica.shizuku.playground

import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.characters.Specialist

sealed class Tile
object None: Tile()
object Open: Tile()
object Hill: Tile()
object Stone: Tile()
object Water: Tile()
data class Lava(var lastingFor: Int): Tile()
object Tree: Tile()
object Ruin: Tile()
data class Shelter(var cap: Int): Tile() {
    private val inside = mutableListOf<AbstractCharacter>()

    val isFull: Boolean
        get() = inside.size >= cap
    fun joinAPlayer(char: AbstractCharacter): Boolean {
        return if (!isFull) {
            inside.add(char); true
        } else false
    }
    fun leaveAplayer(char: AbstractCharacter): Boolean {
        return if (inside.contains(char)) {
            inside.remove(char); true
        } else false
    }
    fun hasPlayer(char: AbstractCharacter): Boolean {
        return inside.contains(char)
    }
    fun extend(specialist: Specialist, increase: Int): Boolean {
        return if (specialist.extendCount > 0) {
            specialist.extendCount -= 1
            cap += increase; true
        } else false
    }
}
data class Village(val size: Size): Tile()
data class Stair(val dir: Direction): Tile()
data class Lock(val controlled: MutableList<Coordinate>, var energy: Int): Tile()
data class Monster(
    var stamina: Int, val atk: Int, var rank: Int,
    var defeatGetStamina: Int,
    var defeatGetGem: Int,
    var defeatGetGold: Int,
): Tile()