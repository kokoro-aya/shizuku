package org.ironica.shizuku.playground.characters

import org.ironica.shizuku.corelanguage.literals.PortionItemLiteral
import org.ironica.shizuku.corelanguage.literals.WeaponItemLiteral
import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.shop.PortionItem
import org.ironica.shizuku.playground.shop.WeaponItem

interface AbstractCharacter {

    val id: Int
    var dir: Direction
    var stamina: Int

    var hasJustSteppedIntoPortal: Boolean

    var atk: Int
    var weaponItem: WeaponItem?

    var collectedGems: Int
    var collectedGolds: Int
    var goldsInBag: Int

    var inWaterForTurns: Int
    var inLavaForTurns: Int


    fun turnLeft(): Boolean
    fun turnRight(): Boolean
    fun moveForward(): Boolean
    fun collectGem(): Boolean
    fun toggleSwitch(): Boolean
    fun takeGold(): Boolean
    fun dropGold(value: Int): Boolean

    fun stepIntoPortal(): Boolean

    fun jump(): Boolean

    fun changeColor(color: Color): Boolean

    fun kill(): Boolean

    fun fightAgainstMonster(): Boolean
    fun buy(portion: PortionItemLiteral): Boolean
    fun buy(weapon: WeaponItemLiteral): Boolean

    fun dance1(): Boolean
    fun dance2(): Boolean
    fun dance3(): Boolean
    fun dance4(): Boolean
    fun dance5(): Boolean

    fun setUpShelter(): Boolean

    val isOnGem: Boolean
    val isOnOpenedSwitch: Boolean
    val isOnClosedSwitch: Boolean
    val isOnGold: Boolean
    val isOnPortion: Boolean

    val isInVillage: Boolean
    val isInShelter: Boolean
    val isOnHill: Boolean
    val isInForest: Boolean
    val isInWater: Boolean
    val isInLava: Boolean
    val isInRuin: Boolean
    val isAgainstMonster: Boolean

    val isInSnowy: Boolean
    val isInCold: Boolean
    val isInValley: Boolean
    val isInPlains: Boolean
    val isInSwamp: Boolean
    val isInDesert: Boolean
    val isInBadland: Boolean

    val isOnPlatform: Boolean
    val isOnPortal: Boolean
    val isBlocked: Boolean
    val isBlockedLeft: Boolean
    val isBlockedRight: Boolean
    val collectedGem: Int
    val goldInBag: Int

    val isAlive: Boolean
    val isDead: Boolean

    val isInWinter: Boolean
}