package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.items.Item
import org.ironica.shizuku.playground.items.Platform
import org.ironica.shizuku.playground.items.Portal
import org.ironica.shizuku.playground.playground.Playground

interface AbstractWorld {

    fun place(player: AbstractCharacter, facing: Direction, at: Coordinate): Boolean
    fun place(item: Item, at: Coordinate): Boolean
    fun place(platform: Platform, at: Coordinate): Boolean
    fun place(portal: Portal, atStart: Coordinate, atEnd: Coordinate): Boolean
    fun place(block: Tile, at: Coordinate): Boolean
    fun placeStair(facing: Direction, at: Coordinate): Boolean

    fun setBiome(biome: Biome, at: Coordinate): Boolean
    fun levelDown(at: Coordinate): Boolean

    fun waitATurn(): Boolean
    fun win(): Boolean
    fun lose(): Boolean

    val allPossibleCoordinates: List<Coordinate>

    fun existingCharacters(at: List<Coordinate>): List<AbstractCharacter>
    fun removeAllBlocks(at: Coordinate): Boolean


}