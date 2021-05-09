package org.ironica.shizuku.manager

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.characters.AbstractPlayer
import org.ironica.shizuku.playground.message.GameStatus
import org.ironica.shizuku.playground.playground.Playground
import org.ironica.shizuku.playground.world.*

interface AbstractManager {
    val playground: Playground
    var consoleLog: String
    var special: String

    val defaultPlayer: AbstractPlayer?
    val defaultWorld: AbstractWorld?

    fun printGrid() {
        return playground.printGrid()
    }

    // Player common properties
    fun isOnGem(player: AbstractPlayer): () -> Boolean
    fun isOnOpenedSwitch(player: AbstractPlayer): () -> Boolean
    fun isOnClosedSwitch(player: AbstractPlayer): () -> Boolean
    fun isOnBeeper(player: AbstractPlayer): () -> Boolean
    fun isAtHome(player: AbstractPlayer): () -> Boolean
    fun isInDesert(player: AbstractPlayer): () -> Boolean
    fun isInForest(player: AbstractPlayer): () -> Boolean
    fun isInWater(player: AbstractPlayer): () -> Boolean
    fun isInLava(player: AbstractPlayer): () -> Boolean
    fun isOnPortal(player: AbstractPlayer): () -> Boolean
    fun isBlocked(player: AbstractPlayer): () -> Boolean
    fun isBlockedLeft(player: AbstractPlayer): () -> Boolean
    fun isBlockedRight(player: AbstractPlayer): () -> Boolean
    fun collectedGem(player: AbstractPlayer): () -> Int

    fun isAlive(player: AbstractPlayer): () -> Boolean
    fun isDead(player: AbstractPlayer): () -> Boolean

    fun isInWinter(player: AbstractPlayer): () -> Boolean
    fun isInShelter(player: AbstractPlayer): () -> Boolean

    // Player common methods
    fun turnLeft(player: AbstractPlayer): () -> Boolean
    fun turnRight(player: AbstractPlayer): () -> Boolean
    fun moveForward(player: AbstractPlayer): () -> Boolean
    fun collectGem(player: AbstractPlayer): () -> Boolean
    fun toggleSwitch(player: AbstractPlayer): () -> Boolean
    fun takeBeeper(player: AbstractPlayer): () -> Boolean
    fun dropBeeper(player: AbstractPlayer): () -> Boolean

    fun kill(id: Int): () -> Boolean

    fun setUpShelter(player: AbstractPlayer): () -> Boolean

    // Portal common properties
    fun isActive(portal: PortalObject): () -> Boolean

    // World common methods
    fun place(world: AbstractWorld, player: PlayerObject, facing: Direction, atColumn: Int, row: Int)
    fun place(world: AbstractWorld, player: PlayerObject, facing: Direction, at: CoordinateObject)
    fun place(world: AbstractWorld, item: ItemObject, atColumn: Int, row: Int)
    fun place(world: AbstractWorld, item: ItemObject, at: CoordinateObject)
    fun place(world: AbstractWorld, platform: PlatformObject, atColumn: Int, row: Int)
    fun place(world: AbstractWorld, platform: PlatformObject, at: CoordinateObject)
    fun place(world: AbstractWorld, portal: PortalObject, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int)
    fun place(world: AbstractWorld, portal: PortalObject, atStart: CoordinateObject, atEnd: CoordinateObject)
    fun place(world: AbstractWorld, stair: IntermediateItemObject, facing: Direction, atColumn: Int, row: Int)
    fun place(world: AbstractWorld, stair: IntermediateItemObject, facing: Direction, at: CoordinateObject)
    fun place(world: AbstractWorld, block: BlockObject, atColumn: Int, row: Int)
    fun place(world: AbstractWorld, block: BlockObject, at: CoordinateObject)

    fun levelDown(world: AbstractWorld, atColumn: Int, row: Int)
    fun levelDown(world: AbstractWorld, at: CoordinateObject)

    fun wait(world: AbstractWorld, turns: Int)
    fun win(world: AbstractWorld)
    fun lose(world: AbstractWorld)

    // Create object instances
    fun initializePlayer(name: String): PlayerObject
    fun initializeSpecialist(name: String): PlayerObject
    fun initializeGem(): ItemObject
    fun initializeBeeper(): ItemObject
    fun initializeSwitch(off: Boolean): ItemObject
    fun initializeSwitch(): ItemObject
    fun initializePlatform(level: Int): PlatformObject
    fun initializePortal(active: Boolean, color: Color): PortalObject
    fun initializeStair(): IntermediateItemObject
    fun initializeBlock(): BlockObject
    fun initializeBlocked(): BlockObject
    fun initializeWater(): BlockObject
    fun initializeLava(): BlockObject
    fun initializeMountain(): BlockObject
    fun initializeStone(): BlockObject
    fun initializeTree(): BlockObject
    fun initializeDesert(): BlockObject
    fun initializeHome(): BlockObject
    fun initializeCoordinate(column: Int, row: Int): CoordinateObject

    fun print(lmsg: List<String>) {
        lmsg.forEach { print("$it ")}
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
            special = "WIN"
            appendEntry()
            true
        } else false
    }

    fun gameIsPending(): Boolean {
        return if (playground.status == GameStatus.PENDING) true else false
    }

    fun appendEntry()
}