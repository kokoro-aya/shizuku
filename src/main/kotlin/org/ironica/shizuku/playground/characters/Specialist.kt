package org.ironica.shizuku.playground.characters

import org.ironica.shizuku.playground.Direction

class Specialist(
    id: Int,
    dir: Direction,
    stamina: Int
): Player(id, dir, stamina) {

    var extendCount: Int = 0

    override fun jump(): Boolean = throw UnsupportedOperationException("Specialist is not supposed to jump over tiles.")
    
    fun turnLockUp(): Boolean = playground.specialistTurnLockUp(this)

    fun turnLockDown(): Boolean = playground.specialistTurnLockDown(this)

    fun extendShelter(): Boolean = playground.specialistExtendShelter(this)
}

// Add exp level to specialist and lock requirement