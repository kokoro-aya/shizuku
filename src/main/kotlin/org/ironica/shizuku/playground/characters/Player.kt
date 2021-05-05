package org.ironica.shizuku.playground.characters

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.data.Coordinate

open class Player(
    val id: Int,
    var dir: Direction,
    var stamina: Int
): AbstractPlayer {

    var inWaterForTurns: Int = 0
    var inLaveForTurns: Int = 0

    override fun turnLeft(): Boolean {
        TODO("Not yet implemented")
    }

    override fun turnRight(): Boolean {
        TODO("Not yet implemented")
    }

    override fun moveForward(): Boolean {
        TODO("Not yet implemented")
    }

    override fun collectGem(): Boolean {
        TODO("Not yet implemented")
    }

    override fun toggleSwitch(): Boolean {
        TODO("Not yet implemented")
    }

    override fun takeBeeper(): Boolean {
        TODO("Not yet implemented")
    }

    override fun dropBeeper(): Boolean {
        TODO("Not yet implemented")
    }

    override fun changeColor(color: Color): Boolean {
        TODO("Not yet implemented")
    }

    override fun jump(): Boolean {
        TODO("Not yet implemented")
    }

    override fun kill(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setUpShelter(): Boolean {
        TODO("Not yet implemented")
    }

    override val isOnGem: Boolean
        get() = TODO("Not yet implemented")
    override val isOnOpenedSwitch: Boolean
        get() = TODO("Not yet implemented")
    override val isOnClosedSwitch: Boolean
        get() = TODO("Not yet implemented")
    override val isOnBeeper: Boolean
        get() = TODO("Not yet implemented")
    override val isAtHome: Boolean
        get() = TODO("Not yet implemented")
    override val isInDesert: Boolean
        get() = TODO("Not yet implemented")
    override val isInForest: Boolean
        get() = TODO("Not yet implemented")
    override val isInWater: Boolean
        get() = TODO("Not yet implemented")
    override val isInLava: Boolean
        get() = TODO("Not yet implemented")
    override val isOnPortal: Boolean
        get() = TODO("Not yet implemented")
    override val isBlocked: Boolean
        get() = TODO("Not yet implemented")
    override val isBlockedLeft: Boolean
        get() = TODO("Not yet implemented")
    override val isBlockedRight: Boolean
        get() = TODO("Not yet implemented")
    override val collectedGem: Int
        get() = TODO("Not yet implemented")
    override val isAlive: Boolean
        get() = TODO("Not yet implemented")
    override val isDead: Boolean
        get() = TODO("Not yet implemented")
    override val isInWinter: Boolean
        get() = TODO("Not yet implemented")
    override val isInShelter: Boolean
        get() = TODO("Not yet implemented")
}
