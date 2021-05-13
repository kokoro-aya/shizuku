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

    val defaultCharacter: AbstractCharacter?

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

    fun appendEntry()

    // Player common properties
    fun isOnGem(character: AbstractCharacter): () -> Boolean
    fun isOnOpenedSwitch(character: AbstractCharacter): () -> Boolean
    fun isOnClosedSwitch(character: AbstractCharacter): () -> Boolean
    fun isOnGold(character: AbstractCharacter): () -> Boolean
    fun isOnPortion(character: AbstractCharacter): () -> Boolean
    fun isInVillage(character: AbstractCharacter): () -> Boolean
    fun isInShelter(character: AbstractCharacter): () -> Boolean
    fun isOnHill(character: AbstractCharacter): () -> Boolean
    fun isInForest(character: AbstractCharacter): () -> Boolean
    fun isInWater(character: AbstractCharacter): () -> Boolean
    fun isInLava(character: AbstractCharacter): () -> Boolean
    fun isInRuin(character: AbstractCharacter): () -> Boolean
    fun isAgainstMonster(character: AbstractCharacter): () -> Boolean
    fun isInSnowy(character: AbstractCharacter): () -> Boolean
    fun isInCold(character: AbstractCharacter): () -> Boolean
    fun isInValley(character: AbstractCharacter): () -> Boolean
    fun isInPlains(character: AbstractCharacter): () -> Boolean
    fun isInSwamp(character: AbstractCharacter): () -> Boolean
    fun isInDesert(character: AbstractCharacter): () -> Boolean
    fun isInBadland(character: AbstractCharacter): () -> Boolean
    fun isOnPlatform(character: AbstractCharacter): () -> Boolean
    fun isOnPortal(character: AbstractCharacter): () -> Boolean
    fun isBlocked(character: AbstractCharacter): () -> Boolean
    fun isBlockedLeft(character: AbstractCharacter): () -> Boolean
    fun isBlockedRight(character: AbstractCharacter): () -> Boolean
    fun collectedGem(character: AbstractCharacter): () -> Int
    fun goldInBag(character: AbstractCharacter): () -> Int
    fun isAlive(character: AbstractCharacter): () -> Boolean
    fun isDead(character: AbstractCharacter): () -> Boolean
    fun isInWinter(character: AbstractCharacter): () -> Boolean

    // Player common methods
    fun turnLeft(character: AbstractCharacter): () -> Boolean
    fun turnRight(character: AbstractCharacter): () -> Boolean
    fun moveForward(character: AbstractCharacter): () -> Boolean
    fun collectGem(character: AbstractCharacter): () -> Boolean
    fun toggleSwitch(character: AbstractCharacter): () -> Boolean
    fun takeBeeper(character: AbstractCharacter): () -> Boolean
    fun dropBeeper(character: AbstractCharacter): () -> Boolean

    fun jump(character: AbstractCharacter): () -> Boolean

    fun changeColor(character: AbstractCharacter, color: Color): () -> Boolean

    fun kill(character: AbstractCharacter): () -> Boolean

    fun fightAgainstMonster(character: AbstractCharacter): () -> Boolean

    fun danceAsIfNobodyIsWatching(character: AbstractCharacter): () -> Boolean
    fun turnUp(character: AbstractCharacter): () -> Boolean
    fun breakItDown(character: AbstractCharacter): () -> Boolean
    fun grumbleGrumble(character: AbstractCharacter): () -> Boolean
    fun argh(character: AbstractCharacter): () -> Boolean

    fun setUpShelter(character: AbstractCharacter): () -> Boolean

    // Portal common properties
    fun isActive(portal: Portal): () -> Boolean
    fun toggle(portal: Portal): () -> Boolean

    // World common properties
    fun allPossibleCoordinates(world: AbstractWorld): List<Coordinate>

    // World common methods
    fun place(world: AbstractWorld, player: AbstractCharacter, facing: Direction, atColumn: Int, row: Int): () -> Boolean
    fun place(world: AbstractWorld, player: AbstractCharacter, facing: Direction, at: Coordinate): () -> Boolean
    fun place(world: AbstractWorld, item: Item, atColumn: Int, row: Int): () -> Boolean
    fun place(world: AbstractWorld, item: Item, at: Coordinate): () -> Boolean
    fun place(world: AbstractWorld, platform: Platform, atColumn: Int, row: Int): () -> Boolean
    fun place(world: AbstractWorld, platform: Platform, at: Coordinate): () -> Boolean
    fun place(world: AbstractWorld, portal: Portal, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int): () -> Boolean
    fun place(world: AbstractWorld, portal: Portal, atStart: Coordinate, atEnd: Coordinate): () -> Boolean
    fun place(world: AbstractWorld, block: Tile, atColumn: Int, row: Int): () -> Boolean
    fun place(world: AbstractWorld, block: Tile, at: Coordinate): () -> Boolean
    fun place(world: AbstractWorld, stair: Stair, facing: Direction, atColumn: Int, row: Int): () -> Boolean
    fun place(world: AbstractWorld, stair: Stair, facing: Direction, at: Coordinate): () -> Boolean

    fun setBiome(world: AbstractWorld, biome: Biome, atColumn: Int, row: Int): () -> Boolean
    fun setBiome(world: AbstractWorld, biome: Biome, at: Coordinate): () -> Boolean

    fun levelDown(world: AbstractWorld, atColumn: Int, row: Int): () -> Boolean
    fun levelDown(world: AbstractWorld, at: Coordinate): () -> Boolean

    fun waitATurn(world: AbstractWorld): () -> Boolean
    fun win(world: AbstractWorld): () -> Boolean
    fun lose(world: AbstractWorld): () -> Boolean

    fun existingCharacters(world: AbstractWorld, at: List<Coordinate>): () -> List<AbstractCharacter>
    fun removeAllBlocks(world: AbstractWorld, atColumn: Int, row: Int): () -> Boolean
    fun removeAllBlocks(at: Coordinate): () -> Boolean

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