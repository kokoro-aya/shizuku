package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.characters.Player

class World: AbstractWorld {
    override fun place(player: Player, atColumn: Int, row: Int) {
        TODO("Not yet implemented")
    }

    override fun place(player: Player, at: CoordinateObject) {
        TODO("Not yet implemented")
    }

    override fun place(item: ItemObject, atColumn: Int, row: Int) {
        TODO("Not yet implemented")
    }

    override fun place(item: ItemObject, at: CoordinateObject) {
        TODO("Not yet implemented")
    }

    override fun place(platform: PlatformObject, atColumn: Int, row: Int) {
        TODO("Not yet implemented")
    }

    override fun place(platform: PlatformObject, at: CoordinateObject) {
        TODO("Not yet implemented")
    }

    override fun place(portal: PortalObject, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int) {
        TODO("Not yet implemented")
    }

    override fun place(portal: PortalObject, atStart: CoordinateObject, atEnd: CoordinateObject) {
        TODO("Not yet implemented")
    }

    override fun place(stair: IntermediateItemObject, facing: Direction, atColumn: Int, row: Int) {
        TODO("Not yet implemented")
    }

    override fun place(stair: IntermediateItemObject, facing: Direction, at: CoordinateObject) {
        TODO("Not yet implemented")
    }

    override fun place(block: BlockObject, atColumn: Int, row: Int) {
        TODO("Not yet implemented")
    }

    override fun place(block: BlockObject, at: CoordinateObject) {
        TODO("Not yet implemented")
    }

    override fun levelDown(atColumn: Int, row: Int) {
        TODO("Not yet implemented")
    }

    override fun levelDown(at: CoordinateObject) {
        TODO("Not yet implemented")
    }

    override fun wait(turns: Int) {
        TODO("Not yet implemented")
    }

    override fun win() {
        TODO("Not yet implemented")
    }

    override fun lose() {
        TODO("Not yet implemented")
    }

}