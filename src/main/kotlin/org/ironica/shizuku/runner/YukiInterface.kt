package org.ironica.shizuku.runner

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.ironica.shizuku.corelanguage.YukiVisitorImpl
import org.ironica.shizuku.manager.ColorfulManager
import org.ironica.shizuku.manager.MountainousManager
import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.data.*
import org.ironica.shizuku.playground.playground.Playground
import org.ironica.shizuku.runner.initrules.disabled.DisabledFeaturesList
import org.ironica.shizuku.runner.initrules.playgroundrules.AutoFailRule
import org.ironica.shizuku.runner.initrules.playgroundrules.Rules
import org.ironica.shizuku.runner.initrules.preinitialized.PreInitializedList
import org.ironica.shizuku.runner.initrules.specialrules.ChangeBlock
import org.ironica.shizuku.runner.initrules.specialrules.ChangePlatform
import org.ironica.shizuku.runner.initrules.specialrules.SpecialRules
import org.ironica.shizuku.utils.zipThree2DArrayToObjArrayWith
import yukiLexer
import yukiParser

class YukiInterface(
    private val type: String,
    private val code: String,
    private var tiles: Tiles = listOf(),
    grid: Grid,
    itemEnumLayout: Array<Array<ItemEnum>>,
    misc: Array<Array<String>>,
    private val portals: Array<Portal>,
    private val locks: Array<Lock>,
    private val players: Array<PlayerData>,

    private val disabledFeatures: DisabledFeaturesList,
    private val preInitializedObjects: PreInitializedList,
    private val rules: Rules,

    stairs: Array<Stair>,
    platforms: Array<PlatformData>,

    private val additionalGems: Array<GemOrBeeper>,
    private val additionalBeepers: Array<GemOrBeeper>,
    private val specialRules: SpecialRules
) {
    init {
        val layout = convertItemArrayToLayout(
            itemArray = itemEnumLayout,
            gemDisappearIn = rules.gemRules.disappearAfter,
            beeperDisappearIn = rules.beeperRules.disappearAfter,
            portalList = portals,
            platformDataList = platforms,
            platformChangeList = specialRules.changePlatforms
        )
        val miscLayout = convertJsonToMiscLayout(misc, type)
        tiles = combineGridLayoutMiscStairToObj(grid, layout, miscLayout, type)
        addStairsToTile(tiles, stairs)
        injectChangesToTile(tiles, specialRules.changeBlocks)
        assert (locks.all { checkAllControlledByLockAsPlatform(it, platforms) })
    }
    fun start() {
        val input: CharStream = CharStreams.fromString(code)
        val lexer = yukiLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = yukiParser(tokens)
        val tree: ParseTree = parser.top_level()

        val playground = Playground(
            tiles = tiles,
            portals = portals.toMutableList(),
            locks = locks.toList(),
            playerDatas = players,
            additionalGems = additionalGems.toMutableList(),
            additionalBeepers = additionalBeepers.toMutableList(),
            randomInitGems = specialRules.randomInitGem,
            randomInitBeepers = specialRules.randomInitBeepers,
            randomInitPortals = specialRules.randomInitPortals,
            userCollision = rules.userCollision,
            maxTerrainHeight = rules.maxTerrainHeight,
            canStackOnTerrain = rules.canStackOnTerrain,
            canPutBlockOnVoid = rules.canPutBlockOnVoid,
            eraseTerrainCondition = rules.eraseTerrain,
            winCondition = rules.winCondition,
            loseCondition = rules.loseCondition,
            staminaRule = rules.staminaRules,
            swimRules = rules.swimRules,
            lavaRules = rules.lavaRules,
            beeperDisappearAfter = rules.beeperRules.disappearAfter,
            gemDisappearAfter = rules.gemRules.disappearAfter,
            autoFailRule = AutoFailRule(
                beeperDisappear = rules.beeperRules.autoFail,
                gemDisappear = rules.gemRules.autoFail,
            ),
            additionalBeeperOneAfterAnother = rules.additionalBeeperOneAfterAnother,
            additionalGemOneAfterAnother = rules.additionalGemOneAfterAnother,
            defaultPortalEnergy = rules.portalRules.defaultEnergy,
            defaultLockEnergy = rules.lockRules.defaultEnergy,
            decreaseEachUsageOfPortal = rules.portalRules.decreaseEachUsage,
            decreaseEachUsageOfLock = rules.lockRules.decreaseEachUsage,
            seasonRules = rules.seasonRules,
            specialMessages = specialRules.specialMessages.toList(),
        )
        val manager = when(type) {
            "colorful" -> ColorfulManager(playground)
            "mountainous" -> MountainousManager(playground)
            else -> throw Exception("Unsupported game module")
        }
        manager.appendEntry()
        val exec = YukiVisitorImpl(manager, disabledFeatures, preInitializedObjects)
        exec.visit(tree)
    }

    private fun combineGridLayoutMiscStairToObj(
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

    private fun convertItemArrayToLayout(
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

    private fun getPortalInListByCoo(portalList: Array<Portal>, coo: Coordinate): Portal {
        return portalList.firstOrNull { it.coo == coo } ?: throw Exception("Portal undeclared in Portals List!")
    }

    private fun getPlatformInListByCoo(platformList: Array<PlatformData>, platformChangeList: Array<ChangePlatform>, coo: Coordinate): Platform {
        val currentPlatformChanges = platformChangeList
            .filter { it.coo == coo }
            .map { e -> Pair(e.inTurn, e.toLevel) }.sortedBy { it.first }.toTypedArray()
        return Platform(
            level = platformList.firstOrNull { it.coo == coo }?.level ?: throw Exception("Platform undeclared in Platforms List!"),
            changes = if (currentPlatformChanges.isNotEmpty()) currentPlatformChanges.sortedBy { it.first } else null
        )
    }

    private fun addStairsToTile(tiles: Tiles, stairs: Array<Stair>) {
        stairs.forEach { tiles[it.coo.y][it.coo.x].stairs.add(it.dir) }
    }

    private fun injectChangesToTile(tiles: Tiles, changeBlocks: Array<ChangeBlock>) {
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

    private fun convertJsonToMiscLayout(array: Array<Array<String>>, using: String): MiscLayout {
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

    private fun checkAllControlledByLockAsPlatform(lock: Lock, platformList: Array<PlatformData>): Boolean {
        return lock.controlled.all {
            val coo = Coordinate(it.y, it.x)
            platformList.any { it.coo == coo }
        }
    }
}