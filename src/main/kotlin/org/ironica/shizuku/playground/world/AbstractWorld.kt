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

interface AbstractWorld {

    fun place(player: AbstractCharacter, facing: Direction, atColumn: Int, row: Int): Boolean
    fun place(player: AbstractCharacter, facing: Direction, at: Coordinate): Boolean
    fun place(item: Item, atColumn: Int, row: Int): Boolean
    fun place(item: Item, at: Coordinate): Boolean
    fun place(platform: Platform, atColumn: Int, row: Int): Boolean
    fun place(platform: Platform, at: Coordinate): Boolean
    fun place(portal: Portal, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int): Boolean
    fun place(portal: Portal, atStart: Coordinate, atEnd: Coordinate): Boolean
    fun place(block: Tile, atColumn: Int, row: Int): Boolean
    fun place(block: Tile, at: Coordinate): Boolean
    fun place(stair: Stair, facing: Direction, atColumn: Int, row: Int): Boolean
    fun place(stair: Stair, facing: Direction, at: Coordinate): Boolean

    fun levelDown(atColumn: Int, row: Int): Boolean
    fun levelDown(at: Coordinate): Boolean

    fun waitATurn(): Boolean
    fun win(): Boolean
    fun lose(): Boolean

    val allPossibleCoordinates: List<Coordinate>

    fun existingCharacters(at: List<Coordinate>): List<AbstractCharacter>
    fun removeAllBlocks(atColumn: Int, row: Int)
    fun removeAllBlocks(at: Coordinate)


}