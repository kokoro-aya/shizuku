package org.ironica.shizuku.playground.characters

import org.ironica.shizuku.playground.Color

interface AbstractPlayer {
    fun turnLeft(): Boolean
    fun turnRight(): Boolean
    fun moveForward(): Boolean
    fun collectGem(): Boolean
    fun toggleSwitch(): Boolean
    fun takeBeeper(): Boolean
    fun dropBeeper(): Boolean

    fun changeColor(color: Color): Boolean

    fun jump(): Boolean

    fun kill(): Boolean

    fun setUpShelter(): Boolean

    val isOnGem: Boolean
    val isOnOpenedSwitch: Boolean
    val isOnClosedSwitch: Boolean
    val isOnBeeper: Boolean
    val isAtHome: Boolean
    val isInDesert: Boolean
    val isInForest: Boolean
    val isInWater: Boolean
    val isInLava: Boolean
    val isOnPortal: Boolean
    val isBlocked: Boolean
    val isBlockedLeft: Boolean
    val isBlockedRight: Boolean
    val collectedGem: Int

    val isAlive: Boolean
    val isDead: Boolean

    val isInWinter: Boolean
    val isInShelter: Boolean
}