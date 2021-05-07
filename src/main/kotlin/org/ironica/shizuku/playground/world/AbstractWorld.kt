package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.playground.Playground

interface AbstractWorld {

    var playground: Playground

    fun place(player: PlayerObject, facing: Direction, atColumn: Int, row: Int): Boolean
    fun place(player: PlayerObject, facing: Direction, at: CoordinateObject): Boolean
    fun place(item: ItemObject, atColumn: Int, row: Int): Boolean
    fun place(item: ItemObject, at: CoordinateObject): Boolean
    fun place(platform: PlatformObject, atColumn: Int, row: Int): Boolean
    fun place(platform: PlatformObject, at: CoordinateObject): Boolean
    fun place(portal: PortalObject, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int): Boolean
    fun place(portal: PortalObject, atStart: CoordinateObject, atEnd: CoordinateObject): Boolean
    fun place(stair: IntermediateItemObject, facing: Direction, atColumn: Int, row: Int): Boolean
    fun place(stair: IntermediateItemObject, facing: Direction, at: CoordinateObject): Boolean
    fun place(block: BlockObject, atColumn: Int, row: Int): Boolean
    fun place(block: BlockObject, at: CoordinateObject): Boolean

    fun levelDown(atColumn: Int, row: Int): Boolean
    fun levelDown(at: CoordinateObject): Boolean

    fun waitATurn(): Boolean
    fun win(): Boolean
    fun lose(): Boolean
}