package org.ironica.shizuku.playground.items

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Coordinate
import org.ironica.shizuku.playground.Lock
import org.ironica.shizuku.playground.playground.Playground

data class LockObject(
    val color: Color,
    val playground: Playground,
    var lock: Lock?,
) {
    val controlledBy: List<Coordinate>
        get() = playground.lockControlledBy(lock)

    fun setControlled(at: Coordinate): Boolean {
        return playground.lockSetControlled(lock, at)
    }
}
