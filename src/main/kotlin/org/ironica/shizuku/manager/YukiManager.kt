package org.ironica.shizuku.manager

import org.ironica.shizuku.corelanguage.literals.*
import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.items.*
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
    fun isActive(portal: PortalObject): Boolean = portal.isActive
    fun toggle(portal: PortalObject) {
        portal.toggle()
        printGrid()
        appendEntry()
    }

    // Lock common properties
    fun controlledBy(lock: LockObject): List<Coordinate> = lock.controlledBy

    fun setControlled(lock: LockObject, atColumn: Int, row: Int) {
        lock.setControlled(Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }

    fun setControlled(lock: LockObject, at: Coordinate) {
        lock.setControlled(at)
        printGrid()
        appendEntry()
    }

    // World common properties
    fun allPossibleCoordinates(world: AbstractWorld): List<Coordinate>
        = world.allPossibleCoordinates

    // World common methods
    fun place(world: AbstractWorld, player: AbstractCharacter, facing: Direction, atColumn: Int, row: Int) {
        world.place(player, facing, Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, player: AbstractCharacter, facing: Direction, at: Coordinate) {
        world.place(player, facing, at)
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, item: Item, atColumn: Int, row: Int) {
        world.place(item, Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, item: Item, at: Coordinate) {
        world.place(item, at)
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, platform: Platform, atColumn: Int, row: Int) {
        world.place(platform, Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, platform: Platform, at: Coordinate) {
        world.place(platform, at)
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, portal: PortalObject, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int) {
        if (portal.portal == null) {
            val st = Coordinate(atStartColumn, startRow);
            val ed = Coordinate(atEndColumn, endRow)
            portal.portal = Portal(st, ed, portal.color, portal.isActive, playground.portalRules.defaultEnergy)
            world.place(portal.portal!!, st, ed)
            printGrid()
            appendEntry()
        } else throw Exception()
    }
    fun place(world: AbstractWorld, portal: PortalObject, atStart: Coordinate, atEnd: Coordinate) {
        if (portal.portal == null) {
            portal.portal = Portal(atStart, atEnd, portal.color, portal.isActive, playground.portalRules.defaultEnergy)
            world.place(portal.portal!!, atStart, atEnd)
            printGrid()
            appendEntry()
        } else throw Exception()
    }
    fun place(world: AbstractWorld, block: Tile, atColumn: Int, row: Int) {
        world.place(block, Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, block: Tile, at: Coordinate) {
        world.place(block, at)
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, stair: StairObject, facing: Direction, atColumn: Int, row: Int) {
        world.placeStair(facing, Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }
    fun place(world: AbstractWorld, stair: StairObject, facing: Direction, at: Coordinate) {
        world.placeStair(facing, at)
        printGrid()
        appendEntry()
    }

    fun setBiome(world: AbstractWorld, biome: Biome, atColumn: Int, row: Int) {
        world.setBiome(biome, Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }
    fun setBiome(world: AbstractWorld, biome: Biome, at: Coordinate) {
        world.setBiome(biome, at)
        printGrid()
        appendEntry()
    }

    fun levelDown(world: AbstractWorld, atColumn: Int, row: Int) {
        world.levelDown(Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }
    fun levelDown(world: AbstractWorld, at: Coordinate) {
        world.levelDown(at)
        printGrid()
        appendEntry()
    }

    fun waitATurn(world: AbstractWorld) {
        world.waitATurn()
        printGrid()
        appendEntry()
    }
    fun win(world: AbstractWorld) {
        world.win()
        printGrid()
        appendEntry()
    }
    fun lose(world: AbstractWorld) {
        world.lose()
        printGrid()
        appendEntry()
    }

    fun existingCharacters(world: AbstractWorld, at: List<Coordinate>): List<AbstractCharacter>
        = world.existingCharacters(at)
    fun removeAllBlocks(world: AbstractWorld, atColumn: Int, row: Int) {
        world.removeAllBlocks(Coordinate(atColumn, row))
        printGrid()
        appendEntry()
    }
    fun removeAllBlocks(world: AbstractWorld, at: Coordinate) {
        world.removeAllBlocks(at)
        printGrid()
        appendEntry()
    }

    // Create object instances
    fun initPlayer(name: String): PlayerLiteral {

    }
    fun initSpecialist(name: String): SpecialistLiteral {

    }
    fun initGem(): GemLiteral {

    }
    fun initGold(value: Int): GoldLiteral {

    }
    fun initSwitch(off: Boolean): SwitchLiteral {

    }
    fun initSwitch(): SwitchLiteral {

    }
    fun initPlatform(onLevel: Int, controlledBy: Lock): PlatformLiteral {

    }
    fun initPortal(active: Boolean, color: Color): PortalLiteral {

    }
    fun initPortion(size: Size): PortionLiteral {

    }
    fun initTile(): TileLiteral {
        // TODO Detach to different tile types
    }
    fun initLava(cooldown: Int, willDisappear: Boolean): LavaLiteral {

    }
    fun initShelter(): ShelterLiteral {

    }
    fun initVillage(size: Size): VillageLiteral {

    }
    fun initStair(): StairLiteral {

    }
    fun initLock(): LockLiteral {

    }
    fun initMonster(): MonsterLiteral {

    }

    fun Portion(size: Size): PortionLiteral {

    }
    fun Weapon(level: Int): WeaponLiteral {

    }
}