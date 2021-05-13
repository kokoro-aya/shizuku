package org.ironica.shizuku.playground.characters

import org.ironica.shizuku.corelanguage.literals.PortionItemLiteral
import org.ironica.shizuku.corelanguage.literals.WeaponItemLiteral
import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.playground.Playground
import org.ironica.shizuku.playground.shop.WeaponItem

class Specialist(
    override val id: Int,
    override var dir: Direction,
    override var stamina: Int,
    override var atk: Int,
    override var weaponItem: WeaponItem?,
): AbstractCharacter {
    lateinit var playground: Playground

    override var collectedGems = 0
    override var collectedGolds = 0
    override var goldsInBag = 0

    override var inWaterForTurns: Int = 0
    override var inLavaForTurns: Int = 0

    override var hasJustSteppedIntoPortal: Boolean = false

    var extendCount: Int = 0

    override fun turnLeft(): Boolean = playground.characterTurnLeft(this)

    override fun turnRight(): Boolean = playground.characterTurnRight(this)

    override fun moveForward(): Boolean = playground.characterMoveForward(this)

    override fun collectGem(): Boolean = playground.characterCollectGem(this)

    override fun toggleSwitch(): Boolean = playground.characterToggleSwitch(this)

    override fun takeGold(): Boolean = playground.characterTakeGold(this)

    override fun dropGold(value: Int): Boolean = playground.characterDropGold(this, value)

    override fun stepIntoPortal(): Boolean = playground.characterStepIntoPortal(this)

    override fun jump(): Boolean = playground.characterJump(this)

    override fun changeColor(color: Color): Boolean = playground.characterChangeColor(this, color)

    override fun kill(): Boolean = playground.characterKill(this)

    override fun fightAgainstMonster(): Boolean = playground.characterFightAgainstMonster(this)

    override fun buy(portion: PortionItemLiteral): Boolean {
        return playground.characterBuyPortion(this, portion.size)
    }

    override fun buy(weapon: WeaponItemLiteral): Boolean {
        return playground.characterBuyWeapon(this, weapon.id)
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

    override fun setUpShelter(): Boolean = playground.CharacterSetUpShelter(this)

    fun turnLockUp(): Boolean = playground.specialistTurnLockUp(this)

    fun turnLockDown(): Boolean = playground.specialistTurnLockDown(this)

    val isBeforeLock: Boolean
        get() = playground.specialistIsBeforeLock(this)

    override val isOnGem: Boolean
        get() = playground.characterIsOnGem(this)
    override val isOnOpenedSwitch: Boolean
        get() = playground.characterIsOnOpenedSwitch(this)
    override val isOnClosedSwitch: Boolean
        get() = playground.characterIsOnClosedSwitch(this)
    override val isOnGold: Boolean
        get() = playground.characterIsOnGold(this)
    override val isOnPortion: Boolean
        get() = playground.characterIsOnPortion(this)
    override val isInVillage: Boolean
        get() = playground.characterIsInVillage(this)
    override val isInShelter: Boolean
        get() = playground.characterIsInShelter(this)
    override val isOnHill: Boolean
        get() = playground.characterIsOnHill(this)
    override val isInForest: Boolean
        get() = playground.characterIsInForest(this)
    override val isInWater: Boolean
        get() = playground.characterIsInWater(this)
    override val isInLava: Boolean
        get() = playground.characterIsInLava(this)
    override val isInRuin: Boolean
        get() = playground.characterIsInRuin(this)
    override val isAgainstMonster: Boolean
        get() = playground.characterIsAgainstMonster(this)
    override val isInSnowy: Boolean
        get() = playground.characterIsInSnowy(this)
    override val isInCold: Boolean
        get() = playground.characterIsInCold(this)
    override val isInValley: Boolean
        get() = playground.characterIsInValley(this)
    override val isInPlains: Boolean
        get() = playground.characterIsInPlains(this)
    override val isInSwamp: Boolean
        get() = playground.characterIsInSwamp(this)
    override val isInDesert: Boolean
        get() = playground.characterIsInDesert(this)
    override val isInBadland: Boolean
        get() = playground.characterIsInBadland(this)
    override val isOnPlatform: Boolean
        get() = playground.characterIsOnPlatform(this)
    override val isOnPortal: Boolean
        get() = playground.characterIsOnPortal(this)
    override val isBlocked: Boolean
        get() = playground.characterIsBlocked(this)
    override val isBlockedLeft: Boolean
        get() = playground.characterIsBlockedLeft(this)
    override val isBlockedRight: Boolean
        get() = playground.characterIsBlockedRight(this)
    override val collectedGem: Int
        get() = this.collectedGems
    override val goldInBag: Int
        get() = this.goldsInBag
    override val isAlive: Boolean
        get() = stamina > 0
    override val isDead: Boolean
        get() = !isAlive
    override val isInWinter: Boolean
        get() = playground.characterIsInWinter(this)
}