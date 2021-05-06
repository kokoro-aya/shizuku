package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.ItemEnum
import org.ironica.shizuku.playground.data.*
import org.ironica.shizuku.playground.playground.Playground

class World(override var playground: Playground): AbstractWorld {

    override fun place(player: PlayerObject, atColumn: Int, row: Int)
    = playground.worldPlace(this, player = player.player, at = Coordinate(row, atColumn))

    override fun place(player: PlayerObject, at: CoordinateObject)
    = playground.worldPlace(this, player = player.player, at = Coordinate(at.x, at.y))

    private fun convertItemObjectToItem(item: ItemObject): Item {
        return when (item.content) {
            ItemEnum.OPENEDSWITCH -> Switch(on = true)
            ItemEnum.CLOSEDSWITCH -> Switch(on = false)
            ItemEnum.GEM -> Gem(disappearIn = 0)
            ItemEnum.BEEPER -> Beeper(disappearIn = 0)
            ItemEnum.PORTAL -> throw Exception("Operation not supported for portals")
            ItemEnum.PLATFORM -> throw Exception("Operation not supported for platforms")
        }
    }

    override fun place(item: ItemObject, atColumn: Int, row: Int) {
        return playground.worldPlace(this, item = convertItemObjectToItem(item), at = Coordinate(row, atColumn))
    }

    override fun place(item: ItemObject, at: CoordinateObject) {
        return playground.worldPlace(this, item = convertItemObjectToItem(item), at = Coordinate(at.x, at.y))
    }

    override fun place(platform: PlatformObject, atColumn: Int, row: Int) {
        return playground.worldPlace(this, platform = Platform(platform.level), at = Coordinate(row, atColumn))
    }

    override fun place(platform: PlatformObject, at: CoordinateObject) {
        return playground.worldPlace(this, platform = Platform(platform.level), at = Coordinate(at.x, at.y))
    }

    override fun place(portal: PortalObject, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int) {
        return playground.worldPlace(this, portal = Portal(
            coo = Coordinate(startRow, atStartColumn),
            dest = Coordinate(endRow, atEndColumn),
            color = portal.color,
            isActive = portal.initActive
        ))
    }

    override fun place(portal: PortalObject, atStart: CoordinateObject, atEnd: CoordinateObject) {
        return playground.worldPlace(this, portal = Portal(
            coo = Coordinate(atStart.x, atStart.y),
            dest = Coordinate(atEnd.x, atEnd.y),
            color = portal.color,
            isActive = portal.initActive
        ))
    }

    override fun place(stair: IntermediateItemObject, facing: Direction, atColumn: Int, row: Int) {
        return playground.worldPlace(this, stair = Stair(Coordinate(row, atColumn), facing))
    }

    override fun place(stair: IntermediateItemObject, facing: Direction, at: CoordinateObject) {
        return playground.worldPlace(this, stair = Stair(Coordinate(at.x, at.y), facing))
    }

    override fun place(block: BlockObject, atColumn: Int, row: Int) {
        return playground.worldPlace(this, block = block.block, at = Coordinate(row, atColumn))
    }

    override fun place(block: BlockObject, at: CoordinateObject) {
        return playground.worldPlace(this, block = block.block, at = Coordinate(at.x, at.y))
    }

    override fun levelDown(atColumn: Int, row: Int) = playground.levelDown(this, Coordinate(row, atColumn))

    override fun levelDown(at: CoordinateObject) = playground.levelDown(this, Coordinate(at.x, at.y))

    override fun wait(turns: Int) = playground.wait(this, turns)

    override fun win() = playground.setToWin(this)

    override fun lose() = playground.setToLost(this)

}