package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.ItemEnum
import org.ironica.shizuku.playground.Role
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.data.*
import org.ironica.shizuku.playground.playground.Playground

class World(override var playground: Playground): AbstractWorld {

    override fun place(player: PlayerObject, facing: Direction, atColumn: Int, row: Int): Boolean {
        val p = if (player.role == Role.SPECIALIST) {
            Specialist(playground.playerCount, facing, playground.staminaRule.initial)
        } else {
            Player(playground.playerCount, facing, playground.staminaRule.initial)
        }
        return playground.worldPlace(player = p, at = Coordinate(atColumn, row))
    }

    override fun place(player: PlayerObject, facing: Direction, at: CoordinateObject): Boolean {
        return this.place(player, facing, at.x, at.y)
    }

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

    override fun place(item: ItemObject, atColumn: Int, row: Int): Boolean {
        return playground.worldPlace(item = convertItemObjectToItem(item), at = Coordinate(atColumn, row))
    }

    override fun place(item: ItemObject, at: CoordinateObject): Boolean {
        return this.place(item, at.x, at.y)
    }

    override fun place(platform: PlatformObject, atColumn: Int, row: Int): Boolean {
        if (playground.getHeight(atColumn, row) < platform.level) {
            playground.locks.firstOrNull { it == platform.controlledBy.lock }?.controlled?.add(Coordinate(atColumn, row))
                ?: throw Exception()
            return playground.worldPlace(platform = Platform(platform.level), at = Coordinate(atColumn, row))
        }
        return false
    }

    override fun place(platform: PlatformObject, at: CoordinateObject): Boolean {
        return this.place(platform, at.x, at.y)
    }

    override fun place(portal: PortalObject, atStartColumn: Int, startRow: Int, atEndColumn: Int, endRow: Int): Boolean {
        return playground.worldPlace(portal = Portal(
            coo = Coordinate(startRow, atStartColumn),
            dest = Coordinate(endRow, atEndColumn),
            color = portal.color,
            isActive = portal.initActive
        ))
    }

    override fun place(portal: PortalObject, atStart: CoordinateObject, atEnd: CoordinateObject): Boolean {
        return this.place(portal, atStart.x, atStart.y, atEnd.x, atEnd.y)
    }

    override fun place(stair: IntermediateItemObject, facing: Direction, atColumn: Int, row: Int): Boolean {
        return playground.worldPlace(stair = Stair(Coordinate(atColumn, row), facing))
    }

    override fun place(stair: IntermediateItemObject, facing: Direction, at: CoordinateObject): Boolean {
        return this.place(stair, facing, at.x, at.y)
    }

    override fun place(block: BlockObject, atColumn: Int, row: Int): Boolean {
        return playground.worldPlace(block = block.block, at = Coordinate(atColumn, row))
    }

    override fun place(block: BlockObject, at: CoordinateObject): Boolean {
        return this.place(block, at.x, at.y)
    }

    override fun levelDown(atColumn: Int, row: Int): Boolean = playground.levelDown(Coordinate(atColumn, row))

    override fun levelDown(at: CoordinateObject): Boolean = playground.levelDown(Coordinate(at.x, at.y))

    override fun waitATurn(): Boolean = playground.waitATurn()

    override fun win(): Boolean = playground.setToWin()

    override fun lose(): Boolean = playground.setToLost()

}