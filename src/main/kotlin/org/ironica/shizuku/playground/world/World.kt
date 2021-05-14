package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.items.Item
import org.ironica.shizuku.playground.items.Platform
import org.ironica.shizuku.playground.items.Portal
import org.ironica.shizuku.playground.playground.Playground

class World: AbstractWorld {

    lateinit var playground: Playground

    override fun place(player: AbstractCharacter, facing: Direction, at: Coordinate): Boolean {
        return playground.worldPlace(this, player, facing, at)
    }

    override fun place(item: Item, at: Coordinate): Boolean {
        return playground.worldPlace(this, item, at)
    }

    override fun place(platform: Platform, at: Coordinate): Boolean {
        return playground.worldPlace(this, platform, at)
    }

    override fun place(portal: Portal, atStart: Coordinate, atEnd: Coordinate): Boolean {
        return playground.worldPlace(this, portal, atStart, atEnd)
    }

    override fun place(block: Tile, at: Coordinate): Boolean {
        return playground.worldPlace(this, block, at)
    }

    override fun placeStair(facing: Direction, at: Coordinate): Boolean {
        return playground.worldPlaceStair(this, facing, at)
    }

    override fun setBiome(biome: Biome, at: Coordinate): Boolean {
        return playground.worldSetBiome(this, biome, at)
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

    override fun removeAllBlocks(at: Coordinate): Boolean {
        return playground.worldRemoveAllBlocks(this, at)
    }
}