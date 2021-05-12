package org.ironica.shizuku.playground.characters

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.playground.Playground
import org.ironica.shizuku.playground.shop.Weapon

class Player(
    override val id: Int,
    override var dir: Direction,
    override var stamina: Int,
    override var atk: Int,
    override var weapon: Weapon?
): AbstractCharacter {
    lateinit var playground: Playground

    var collectedGems = 0
    var collectedGolds = 0
    var goldsInBag = 0

    var inWaterForTurns: Int = 0
    var inLavaForTurns: Int = 0

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

    override fun stepIntoPortal(): Boolean {
        TODO("Not yet implemented")
    }

    override fun jump(): Boolean {
        TODO("Not yet implemented")
    }

    override fun changeColor(color: Color): Boolean {
        TODO("Not yet implemented")
    }

    override fun kill(): Boolean {
        TODO("Not yet implemented")
    }

    override fun fightAgainstMonster(): Boolean {
        TODO("Not yet implemented")
    }

    override fun dance1(): Boolean {
        TODO("Not yet implemented")
    }

    override fun dance2(): Boolean {
        TODO("Not yet implemented")
    }

    override fun dance3(): Boolean {
        TODO("Not yet implemented")
    }

    override fun dance4(): Boolean {
        TODO("Not yet implemented")
    }

    override fun dance5(): Boolean {
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
    override val isOnGold: Boolean
        get() = TODO("Not yet implemented")
    override val isOnPortion: Boolean
        get() = TODO("Not yet implemented")
    override val isInVillage: Boolean
        get() = TODO("Not yet implemented")
    override val isInShelter: Boolean
        get() = TODO("Not yet implemented")
    override val isOnHill: Boolean
        get() = TODO("Not yet implemented")
    override val isInForest: Boolean
        get() = TODO("Not yet implemented")
    override val isInWater: Boolean
        get() = TODO("Not yet implemented")
    override val isInLava: Boolean
        get() = TODO("Not yet implemented")
    override val isInRuin: Boolean
        get() = TODO("Not yet implemented")
    override val isAgainstMonster: Boolean
        get() = TODO("Not yet implemented")
    override val isInSnowy: Boolean
        get() = TODO("Not yet implemented")
    override val isInCold: Boolean
        get() = TODO("Not yet implemented")
    override val isInValley: Boolean
        get() = TODO("Not yet implemented")
    override val isInPlains: Boolean
        get() = TODO("Not yet implemented")
    override val isInSwamp: Boolean
        get() = TODO("Not yet implemented")
    override val isInDesert: Boolean
        get() = TODO("Not yet implemented")
    override val isInBadland: Boolean
        get() = TODO("Not yet implemented")
    override val isOnPlatform: Boolean
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
    override val goldInBag: Int
        get() = TODO("Not yet implemented")
    override val isAlive: Boolean
        get() = TODO("Not yet implemented")
    override val isDead: Boolean
        get() = TODO("Not yet implemented")
    override val isInWinter: Boolean
        get() = TODO("Not yet implemented")


}