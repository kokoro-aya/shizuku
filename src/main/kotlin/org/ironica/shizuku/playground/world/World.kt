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
        TODO("Not yet implemented")
    }

    override fun place(player: AbstractCharacter, facing: Direction, at: Coordinate): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(item: Item, atColumn: Int, row: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(item: Item, at: Coordinate): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(platform: Platform, atColumn: Int, row: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(platform: Platform, at: Coordinate): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(portal: Portal, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(portal: Portal, atStart: Coordinate, atEnd: Coordinate): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(block: Tile, atColumn: Int, row: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(block: Tile, at: Coordinate): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(stair: Stair, facing: Direction, atColumn: Int, row: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun place(stair: Stair, facing: Direction, at: Coordinate): Boolean {
        TODO("Not yet implemented")
    }

    override fun levelDown(atColumn: Int, row: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun levelDown(at: Coordinate): Boolean {
        TODO("Not yet implemented")
    }

    override fun waitATurn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun win(): Boolean {
        TODO("Not yet implemented")
    }

    override fun lose(): Boolean {
        TODO("Not yet implemented")
    }

    override val allPossibleCoordinates: List<Coordinate>
        get() = TODO("Not yet implemented")

    override fun existingCharacters(at: List<Coordinate>): List<AbstractCharacter> {
        TODO("Not yet implemented")
    }

    override fun removeAllBlocks(atColumn: Int, row: Int) {
        TODO("Not yet implemented")
    }

    override fun removeAllBlocks(at: Coordinate) {
        TODO("Not yet implemented")
    }
}