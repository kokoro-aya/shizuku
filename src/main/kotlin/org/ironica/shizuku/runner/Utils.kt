package org.ironica.shizuku.runner

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.data.*
import org.ironica.shizuku.runner.initrules.specialrules.ChangeBlock
import org.ironica.shizuku.runner.initrules.specialrules.ChangePlatform
import org.ironica.shizuku.utils.zipThree2DArrayToObjArrayWith

fun combineGridLayoutMiscStairToObj(
    grid: Grid,
    layout: Layout,
    misc: MiscLayout,
    using: String,
): Tiles {
    val x = grid.size
    val y = grid[0].size
    assert (layout.size == x && misc.size == x
                && layout[0].size == y && misc[0].size == y
    ) { "Grid, Layout and Misc must have same size!" }
    return zipThree2DArrayToObjArrayWith(grid, layout, misc,
        with = { a, b, c -> GridObject(a, b, c, mutableListOf(), mutableListOf()) }
    )
}

fun convertItemArrayToLayout(
    itemArray: Array<Array<ItemEnum>>,
    gemDisappearIn: Int,
    beeperDisappearIn: Int,
    portalList: Array<Portal>,
    platformDataList: Array<PlatformData>,
    platformChangeList: Array<ChangePlatform>
): Layout {
    return itemArray.mapIndexed { i, line ->
        line.mapIndexed { j, item ->
            when (item) {
                ItemEnum.NONE -> None
                ItemEnum.CLOSEDSWITCH -> Switch(on = false)
                ItemEnum.OPENEDSWITCH -> Switch(on = true)
                ItemEnum.GEM -> Gem(gemDisappearIn)
                ItemEnum.BEEPER -> Beeper(beeperDisappearIn)
                ItemEnum.PORTAL ->
                    getPortalInListByCoo(portalList, Coordinate(j, i))
                ItemEnum.PLATFORM ->
                    getPlatformInListByCoo(platformDataList, platformChangeList, Coordinate(j, i))
            } as Item
        }.toTypedArray()
    }.toTypedArray()
}

fun getPortalInListByCoo(portalList: Array<Portal>, coo: Coordinate): Portal {
    return portalList.firstOrNull { it.coo == coo } ?: throw Exception("Portal undeclared in Portals List!")
}

fun getPlatformInListByCoo(platformList: Array<PlatformData>, platformChangeList: Array<ChangePlatform>, coo: Coordinate): Platform {
    val currentPlatformChanges = platformChangeList
        .filter { it.coo == coo }
        .map { e -> Pair(e.inTurn, e.toLevel) }.sortedBy { it.first }.toTypedArray()
    return Platform(
        level = platformList.firstOrNull { it.coo == coo }?.level ?: throw Exception("Platform undeclared in Platforms List!"),
        changes = if (currentPlatformChanges.isNotEmpty()) currentPlatformChanges.sortedBy { it.first } else null
    )
}

fun addStairsToTile(tiles: Tiles, stairs: Array<Stair>) {
    stairs.forEach { tiles[it.coo.y][it.coo.x].stairs.add(it.dir) }
}

fun injectChangesToTile(tiles: Tiles, changeBlocks: Array<ChangeBlock>) {
    changeBlocks.forEach {
        val tile = tiles[it.coo.y][it.coo.x]
        if (tile.changes == null) {
            val coo = it.coo
            tile.changes = changeBlocks.filter {
                it.coo == coo
            }.map { Pair(it.inTurn, it.changeTo) }.sortedBy { it.first }
        }
    }
}

fun convertJsonToMiscLayout(array: Array<Array<String>>, using: String): MiscLayout {
    return when (using) {
        "colorful" -> array.map { it.map { ColorfulMiscInfo(convertDataToColor(it)) as MiscInfo }.toTypedArray() }.toTypedArray()
        "mountainous" -> array.map { it.map { MountainMiscInfo(it.toIntOrNull()) as MiscInfo }.toTypedArray() }.toTypedArray()
        else -> throw Exception("Unsupported game module")
    }
}

private fun convertDataToColor(data: String): Color {
    return when (data) {
        "BLACK" -> Color.BLACK
        "SILVER" -> Color.SILVER
        "GREY" -> Color.GREY
        "WHITE" -> Color.WHITE
        "RED" -> Color.RED
        "ORANGE" -> Color.ORANGE
        "GOLD" -> Color.GOLD
        "PINK" -> Color.PINK
        "YELLOW" -> Color.YELLOW
        "BEIGE" -> Color.BEIGE
        "BROWN" -> Color.BROWN
        "GREEN" -> Color.GREEN
        "AZURE" -> Color.AZURE
        "CYAN" -> Color.CYAN
        "ALICEBLUE" -> Color.ALICEBLUE
        "PURPLE" -> Color.PURPLE
        else -> throw Exception("Cannot parse data to color")
    }
}

fun checkAllControlledByLockAsPlatform(lock: Lock, platformList: Array<PlatformData>): Boolean {
    return lock.controlled.all {
        val coo = Coordinate(it.y, it.x)
        platformList.any { it.coo == coo }
    }
}