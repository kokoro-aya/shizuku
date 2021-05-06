package org.ironica.shizuku.playground.characters

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.playground.Playground

open class Player(
    override val id: Int,
    override var dir: Direction,
    override var stamina: Int
): AbstractPlayer {

    lateinit var playground: Playground

    var collectedGems = 0
    var beeperInBag = 0

    var inWaterForTurns: Int = 0
    var inLaveForTurns: Int = 0

    override fun turnLeft(): Boolean = playground.playerTurnLeft(this)

    override fun turnRight(): Boolean = playground.playerTurnRight(this)

    override fun moveForward(): Boolean = playground.playerMoveForward(this)

    override fun collectGem(): Boolean = playground.playerCollectGem(this)

    override fun toggleSwitch(): Boolean = playground.playerToggleSwitch(this)

    override fun takeBeeper(): Boolean = playground.playerTakeBeeper(this)

    override fun dropBeeper(): Boolean = playground.playerDropBeeper(this)

    fun changeColor(color: Color): Boolean = playground.playerChangeColor(this, color)

    open fun jump(): Boolean = playground.playerJump(this)

    override fun kill(): Boolean = playground.playerKill(this)

    override fun setUpShelter(): Boolean = playground.playerSetUpShelter(this)

    override val isOnGem: Boolean
        get() = playground.playerIsOnGem(this)
    override val isOnOpenedSwitch: Boolean
        get() = playground.playerIsOpOpenedSwitch(this)
    override val isOnClosedSwitch: Boolean
        get() = playground.playerIsOnClosedSwitch(this)
    override val isOnBeeper: Boolean
        get() = playground.playerIsOnBeeper(this)
    override val isAtHome: Boolean
        get() = playground.playerIsAtHome(this)
    override val isInDesert: Boolean
        get() = playground.playerIsInDesert(this)
    override val isInForest: Boolean
        get() = playground.playerIsInForest(this)
    override val isInWater: Boolean
        get() = playground.playerIsInWater(this)
    override val isInLava: Boolean
        get() = playground.playerIsInLava(this)
    override val isOnPortal: Boolean
        get() = playground.playerIsOnPortal(this)
    override val isBlocked: Boolean
        get() = playground.playerIsBlocked(this)
    override val isBlockedLeft: Boolean
        get() = playground.playerIsBlockedLeft(this)
    override val isBlockedRight: Boolean
        get() = playground.playerIsBlockedRight(this)
    override val collectedGem: Int
        get() = collectedGems
    override val isAlive: Boolean
        get() = stamina > 0
    override val isDead: Boolean
        get() = !isAlive
    override val isInWinter: Boolean
        get() = playground.playerIsInWinter(this)
    override val isInShelter: Boolean
        get() = playground.playerIsInShelter(this)
}
