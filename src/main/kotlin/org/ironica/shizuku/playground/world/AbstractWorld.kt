package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.characters.Player

interface AbstractWorld {
    fun place(player: Player, atColumn: Int, row: Int)
    fun place(player: Player, at: CoordinateObject)
    fun place(item: ItemObject, atColumn: Int, row: Int)
    fun place(item: ItemObject, at: CoordinateObject)
    fun place(platform: PlatformObject, atColumn: Int, row: Int)
    fun place(platform: PlatformObject, at: CoordinateObject)
    fun place(portal: PortalObject, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int)
    fun place(portal: PortalObject, atStart: CoordinateObject, atEnd: CoordinateObject)
    fun place(stair: IntermediateItemObject, facing: Direction, atColumn: Int, row: Int)
    fun place(stair: IntermediateItemObject, facing: Direction, at: CoordinateObject)
    fun place(block: BlockObject, atColumn: Int, row: Int)
    fun place(block: BlockObject, at: CoordinateObject)

    fun levelDown(atColumn: Int, row: Int)
    fun levelDown(at: CoordinateObject)

    fun wait(turns: Int)
    fun win()
    fun lose()
}