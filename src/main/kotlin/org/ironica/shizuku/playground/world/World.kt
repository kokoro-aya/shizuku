package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Coordinate
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.Stair
import org.ironica.shizuku.playground.Tile
import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.items.Item
import org.ironica.shizuku.playground.items.Platform
import org.ironica.shizuku.playground.items.Portal
import org.ironica.shizuku.playground.playground.Playground

class World: AbstractWorld {

    lateinit var playground: Playground

    override fun place(player: AbstractCharacter, facing: Direction, atColumn: Int, row: Int): Boolean {
        return this.place(player, facing, Coordinate(atColumn, row))
    }

    override fun place(player: AbstractCharacter, facing: Direction, at: Coordinate): Boolean {
        return playground.worldPlace(this, player, facing, at)
    }

    override fun place(item: Item, atColumn: Int, row: Int): Boolean {
        return this.place(item, Coordinate(atColumn, row))
    }

    override fun place(item: Item, at: Coordinate): Boolean {
        return playground.worldPlace(this, item, at)
    }

    override fun place(platform: Platform, atColumn: Int, row: Int): Boolean {
        return this.place(platform, Coordinate(atColumn, row))
    }

    override fun place(platform: Platform, at: Coordinate): Boolean {
        return playground.worldPlace(this, platform, at)
    }

    override fun place(portal: Portal, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int): Boolean {
        return this.place(portal, Coordinate(atStartColumn, startRow), Coordinate(atEndColumn, endRow))
    }

    override fun place(portal: Portal, atStart: Coordinate, atEnd: Coordinate): Boolean {
        return playground.worldPlace(this, portal, atStart, atEnd)
    }

    override fun place(block: Tile, atColumn: Int, row: Int): Boolean {
        return this.place(block, Coordinate(atColumn, row))
    }

    override fun place(block: Tile, at: Coordinate): Boolean {
        return playground.worldPlace(this, block, at)
    }

    override fun place(stair: Stair, facing: Direction, atColumn: Int, row: Int): Boolean {
        return this.place(stair, facing, Coordinate(atColumn, row))
    }

    override fun place(stair: Stair, facing: Direction, at: Coordinate): Boolean {
        return playground.worldPlace(this, stair, facing, at)
    }

    override fun levelDown(atColumn: Int, row: Int): Boolean {
        return this.levelDown(Coordinate(atColumn, row))
    }

    override fun levelDown(at: Coordinate): Boolean {
        return playground.worldLevelDown(this, at)
    }

    override fun waitATurn(): Boolean {
        return playground.worldWaitATurn(this)
    }

    override fun win(): Boolean {
        return playground.worldSetToWin(this)
    }

    override fun lose(): Boolean {
        return playground.worldSetToLost(this)
    }

    override val allPossibleCoordinates: List<Coordinate>
        get() = playground.worldAllPossibleCoordinates(this)

    override fun existingCharacters(at: List<Coordinate>): List<AbstractCharacter> {
        return playground.worldExistingCharacters(this, at)
    }

    override fun removeAllBlocks(atColumn: Int, row: Int) {
        return this.removeAllBlocks(Coordinate(atColumn, row))
    }

    override fun removeAllBlocks(at: Coordinate) {
        return playground.worldRemoveAllBlocks(this, at)
    }
}