package org.ironica.shizuku.manager

import org.ironica.shizuku.corelanguage.literals.*
import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.items.Item
import org.ironica.shizuku.playground.items.Platform
import org.ironica.shizuku.playground.items.Portal
import org.ironica.shizuku.playground.message.GameStatus
import org.ironica.shizuku.playground.playground.Playground
import org.ironica.shizuku.playground.world.AbstractWorld

class YukiManager(val gameMode: GameMode, val playground: Playground) {
    var consoleLog: String = ""
    var special: String = ""

    val defaultCharacter: AbstractCharacter? = if (playground.characterCount == 1) playground.characters.keys.first() else null

    fun printGrid() {
        return playground.printGrid()
    }

    fun print(lmsg: List<String>) {
        lmsg.forEach { print("$it ") }
        println()
        lmsg.forEach { consoleLog += it }
        consoleLog += "\n"
        appendEntry()
    }

    fun gameIsWin(): Boolean {
        return if (playground.status == GameStatus.WIN) {
            special = "WIN"
            appendEntry()
            true
        } else false
    }

    fun gameIsLost(): Boolean {
        return if (playground.status == GameStatus.LOST) {
            special = "LOST"
            appendEntry()
            true
        } else false
    }

    fun gameIsPending(): Boolean {
        return playground.status == GameStatus.PENDING
    }

    fun appendEntry() {
        
    }

    // Player common properties
    fun isOnGem(character: AbstractCharacter): Boolean
        = character.isOnGem
    fun isOnOpenedSwitch(character: AbstractCharacter): Boolean
        = character.isOnOpenedSwitch
    fun isOnClosedSwitch(character: AbstractCharacter): Boolean
        = character.isOnClosedSwitch
    fun isOnGold(character: AbstractCharacter): Boolean
        = character.isOnGold
    fun isOnPortion(character: AbstractCharacter): Boolean
        = character.isOnPortion
    fun isInVillage(character: AbstractCharacter): Boolean
        = character.isInVillage
    fun isInShelter(character: AbstractCharacter): Boolean
        = character.isInShelter
    fun isOnHill(character: AbstractCharacter): Boolean
        = character.isOnHill
    fun isInForest(character: AbstractCharacter): Boolean
        = character.isInForest
    fun isInWater(character: AbstractCharacter): Boolean
        = character.isInWinter
    fun isInLava(character: AbstractCharacter): Boolean
        = character.isInLava
    fun isInRuin(character: AbstractCharacter): Boolean
        = character.isInRuin
    fun isAgainstMonster(character: AbstractCharacter): Boolean
        = character.isAgainstMonster
    fun isInSnowy(character: AbstractCharacter): Boolean
        = character.isInSnowy
    fun isInCold(character: AbstractCharacter): Boolean
        = character.isInCold
    fun isInValley(character: AbstractCharacter): Boolean
        = character.isInValley
    fun isInPlains(character: AbstractCharacter): Boolean
        = character.isInPlains
    fun isInSwamp(character: AbstractCharacter): Boolean
        = character.isInSwamp
    fun isInDesert(character: AbstractCharacter): Boolean
        = character.isInDesert
    fun isInBadland(character: AbstractCharacter): Boolean
        = character.isInBadland
    fun isOnPlatform(character: AbstractCharacter): Boolean
        = character.isOnPlatform
    fun isOnPortal(character: AbstractCharacter): Boolean
        = character.isOnPortal
    fun isBlocked(character: AbstractCharacter): Boolean
        = character.isBlocked
    fun isBlockedLeft(character: AbstractCharacter): Boolean
        = character.isBlockedLeft
    fun isBlockedRight(character: AbstractCharacter): Boolean
        = character.isBlockedRight
    fun collectedGem(character: AbstractCharacter): Int
        = character.collectedGem
    fun goldInBag(character: AbstractCharacter): Int
        = character.goldInBag
    fun isAlive(character: AbstractCharacter): Boolean
        = character.isAlive
    fun isDead(character: AbstractCharacter): Boolean
        = character.isDead
    fun isInWinter(character: AbstractCharacter): Boolean
        = character.isInWinter

    // Player common methods
    fun turnLeft(character: AbstractCharacter) {
        character.turnLeft()
        printGrid()
        appendEntry()
    }
    fun turnRight(character: AbstractCharacter) {
        character.turnRight()
        printGrid()
        appendEntry()
    }
    fun moveForward(character: AbstractCharacter) {
        character.moveForward()
        printGrid()
        appendEntry()
        if (character.isOnPortal && !character.hasJustSteppedIntoPortal) {
            character.stepIntoPortal()
            printGrid()
            appendEntry()
        }
    }
    fun collectGem(character: AbstractCharacter) {
        character.collectGem()
        printGrid()
        this.special = "GEM"
        appendEntry()
    }
    fun toggleSwitch(character: AbstractCharacter) {
        character.toggleSwitch()
        printGrid()
        this.special = "SWITCH"
        appendEntry()
    }
    fun takeGold(character: AbstractCharacter) {
        character.takeGold()
        printGrid()
        this.special = "TAKEGOLD"
    }
    fun dropGold(character: AbstractCharacter, value: Int) {
        character.dropGold(value)
        printGrid()
        this.special = "DROPGOLD"
        appendEntry()
    }

    fun jump(character: AbstractCharacter) {
        character.jump()
        printGrid()
        appendEntry()
    }

    fun changeColor(character: AbstractCharacter, color: Color) {
        character.changeColor(color)
        printGrid()
        appendEntry()
    }

    fun kill(character: AbstractCharacter) {
        character.kill()
        printGrid()
        this.special = "SUICIDED"
        appendEntry()
    }

    fun fightAgainstMonster(character: AbstractCharacter) {
        character.fightAgainstMonster()
        printGrid()
        this.special = "FIGHTINGAGAINSTMONSTER"
        appendEntry()
    }

    fun buy(character: AbstractCharacter, portion: PortionItemLiteral) {
        character.buy(portion)
        printGrid()
        appendEntry()
    }
    fun buy(character: AbstractCharacter, weapon: WeaponItemLiteral) {
        character.buy(weapon)
        printGrid()
        appendEntry()
    }

    fun danceAsIfNobodyIsWatching(character: AbstractCharacter) = character.dance1()
    fun turnUp(character: AbstractCharacter) = character.dance2()
    fun breakItDown(character: AbstractCharacter) = character.dance3()
    fun grumbleGrumble(character: AbstractCharacter) = character.dance4()
    fun argh(character: AbstractCharacter) = character.dance5()

    fun setUpShelter(character: AbstractCharacter) {
        character.setUpShelter()
        printGrid()
        appendEntry()
    }

    // Portal common properties
    fun isActive(portal: Portal): Boolean
    fun toggle(portal: Portal): Boolean

    // World common properties
    fun allPossibleCoordinates(world: AbstractWorld): List<Coordinate>

    // World common methods
    fun place(world: AbstractWorld, player: AbstractCharacter, facing: Direction, atColumn: Int, row: Int): Boolean
    fun place(world: AbstractWorld, player: AbstractCharacter, facing: Direction, at: Coordinate): Boolean
    fun place(world: AbstractWorld, item: Item, atColumn: Int, row: Int): Boolean
    fun place(world: AbstractWorld, item: Item, at: Coordinate): Boolean
    fun place(world: AbstractWorld, platform: Platform, atColumn: Int, row: Int): Boolean
    fun place(world: AbstractWorld, platform: Platform, at: Coordinate): Boolean
    fun place(world: AbstractWorld, portal: Portal, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int): Boolean
    fun place(world: AbstractWorld, portal: Portal, atStart: Coordinate, atEnd: Coordinate): Boolean
    fun place(world: AbstractWorld, block: Tile, atColumn: Int, row: Int): Boolean
    fun place(world: AbstractWorld, block: Tile, at: Coordinate): Boolean
    fun place(world: AbstractWorld, stair: Stair, facing: Direction, atColumn: Int, row: Int): Boolean
    fun place(world: AbstractWorld, stair: Stair, facing: Direction, at: Coordinate): Boolean

    fun setBiome(world: AbstractWorld, biome: Biome, atColumn: Int, row: Int): Boolean
    fun setBiome(world: AbstractWorld, biome: Biome, at: Coordinate): Boolean

    fun levelDown(world: AbstractWorld, atColumn: Int, row: Int): Boolean
    fun levelDown(world: AbstractWorld, at: Coordinate): Boolean

    fun waitATurn(world: AbstractWorld): Boolean
    fun win(world: AbstractWorld): Boolean
    fun lose(world: AbstractWorld): Boolean

    fun existingCharacters(world: AbstractWorld, at: List<Coordinate>): () -> List<AbstractCharacter>
    fun removeAllBlocks(world: AbstractWorld, atColumn: Int, row: Int): Boolean
    fun removeAllBlocks(at: Coordinate): Boolean

    // Create object instances
    fun Player(name: String): PlayerLiteral
    fun Specialist(name: String): SpecialistLiteral
    fun Gem(): GemLiteral
    fun Gold(value: Int): GoldLiteral
    fun Switch(off: Boolean): SwitchLiteral
    fun Switch(): SwitchLiteral
    fun Platform(onLevel: Int, controlledBy: Lock): PlatformLiteral
    fun Portal(active: Boolean, color: Color): PortalLiteral
    fun Portion(size: Size): PortionLiteral
    fun Tile(): TileLiteral
    fun Lava(cooldown: Int, willDisappear: Boolean): LavaLiteral
    fun Shelter(): ShelterLiteral
    fun Village(size: Size): VillageLiteral
    fun Stair(): StairLiteral
    fun Lock(): LockLiteral
    fun Monster(): MonsterLiteral

    fun Portion(size: Size): PortionLiteral
    fun Weapon(level: Int): WeaponLiteral
}