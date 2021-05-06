package org.ironica.shizuku.playground.data

import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist

data class Shelter(
    val x: Int,
    val y: Int,
    var availableFor: Int,
) {
    private val inside = mutableListOf<Player>()
    fun locateXY(): Pair<Int, Int> = Pair(x, y)

    val isFull: Boolean
        get() = inside.size >= availableFor

    fun joinAPlayer(player: Player) {
        assert(!isFull)
        inside.add(player)
    }
    fun leaveAPlayer(player: Player) {
        assert(inside.contains(player))
        inside.remove(player)
    }
    fun hasPlayer(player: Player): Boolean {
        return inside.contains(player)
    }
    fun extend(specialist: Specialist, increase: Int) {
        if (specialist.extendCount > 0)
            availableFor += increase
    }
}
