package org.ironica.shizuku.runner

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.ironica.shizuku.corelanguage.YukiVisitorImpl
import org.ironica.shizuku.manager.ColorfulManager
import org.ironica.shizuku.manager.MountainousManager
import org.ironica.shizuku.playground.Biomes
import org.ironica.shizuku.playground.Block
import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.tile.*
import org.ironica.shizuku.playground.playground.Playground
import org.ironica.shizuku.runner.data.*
import org.ironica.shizuku.runner.initrules.disabled.DisabledFeaturesList
import org.ironica.shizuku.runner.initrules.playgroundrules.AutoFailRule
import org.ironica.shizuku.runner.initrules.playgroundrules.Rules
import org.ironica.shizuku.runner.initrules.preinitialized.PreInitializedList
import org.ironica.shizuku.runner.initrules.specialrules.ChangeBlock
import org.ironica.shizuku.runner.initrules.specialrules.ChangePlatform
import org.ironica.shizuku.runner.initrules.specialrules.SpecialRules
import org.ironica.shizuku.utils.zipNine2DArrayToObjArrayWith
import yukiLexer
import yukiParser

class YukiInterface(
    private val type: String,
    private val code: String,
    private var tiles: Tiles = listOf(),
    grid: Grid,
    itemEnumLayout: Array<Array<Array<ItemEnum>>>,
    misc: Array<Array<String>>,
    private val portals: Array<Portal>,
    private val lockdatas: Array<LockData>,
    private var locks: MutableList<Lock>,
    private val players: Array<PlayerData>,

    private val disabledFeatures: DisabledFeaturesList,
    private val preInitializedObjects: PreInitializedList,
    private val rules: Rules,

    biomes: Array<Array<Biomes>>,

    stairs: Array<StairData>,
    platforms: Array<PlatformData>,
    ruins: Array<RuinData>,
    shelters: Array<ShelterData>,
    villages: Array<VillageData>,

    private val additionalGems: Array<GemOrBeeper>,
    private val additionalBeepers: Array<GemOrBeeper>,
    private val specialRules: SpecialRules
) {
    init {
        val itemLayout = convertItemArrayToLayout(
            itemArray = itemEnumLayout,
            gemDisappearIn = rules.gemRules.disappearAfter,
            beeperDisappearIn = rules.beeperRules.disappearAfter,
            portalList = portals,
            platformDataList = platforms,
            platformChangeList = specialRules.changePlatforms
        )
        val levelLayout = convertJsonToLevelLayout(misc, using = type)
        val colorLayout = convertJsonToColorLayout(misc, using = type)
        val switches = convertItemLayoutToSwitches(itemLayout)
        val gems = convertItemLayoutToGems(itemLayout)
        val beepers = convertItemLayoutToBeepers(itemLayout)
        val portals = convertItemLayoutToPortals(itemLayout)
        val platformss = convertItemLayoutToPlatform(itemLayout)
        val layouts = convertGridToTiles(grid, ruins, shelters, villages, stairs, lockdatas)
        tiles = combineTilesLevelColorBiomesItemsToObj(
            tiles = layouts,
            levels = levelLayout,
            colors = colorLayout,
            biomes = biomes,
            switches = switches,
            gems = gems,
            beepers = beepers,
            portals = portals,
            platforms = platformss,
        )
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

    private fun convertGridToTiles(
        grid: Grid,
        ruins: Array<RuinData>,
        shelters: Array<ShelterData>,
        villages: Array<VillageData>,
        stairs: Array<StairData>,
        lockdatas: Array<LockData>,
    ): Array<Array<Layout>> {
        return grid.mapIndexed { i, line ->
            line.mapIndexed { j, block -> when (block) {
                Block.NONE -> None
                Block.OPEN -> Open
                Block.HILL -> Hill
                Block.STONE -> Stone
                Block.WATER -> Water
                Block.LAVA -> Lava(existsFor = 0)
                Block.TREE -> Tree
                Block.RUIN -> {
                    Ruin(ruins.firstOrNull { it.coo.x == j && it.coo.y == i }?.type
                        ?: throw Exception())
                }
                Block.SHELTER -> {
                    Shelter(shelters.firstOrNull { it.coo.x == j && it.coo.y == i }?.availableFor
                        ?: throw Exception())
                }
                Block.VILLAGE -> {
                    Village(villages.firstOrNull { it.coo.x == j && it.coo.y == i }?.size
                        ?: throw Exception())
                }
                Block.STAIR -> {
                    Stair(stairs.firstOrNull { it.coo.x == j && it.coo.y == i }?.dir
                        ?: throw Exception())
                }
                Block.LOCK -> {
                    lockdatas.firstOrNull { it.coo.x == j && it.coo.y == i }?.let {
                        val l = Lock(it.controlled, 0)
                        locks.add(l)
                        l
                    } ?: throw Exception()
                }
            } }.toTypedArray()
        }.toTypedArray()
    }

    private fun combineTilesLevelColorBiomesItemsToObj(
        tiles: Array<Array<Layout>>,
        levels: Array<Array<Int>>,
        colors: Array<Array<Color>>,
        biomes: Array<Array<Biomes>>,
        switches: Array<Array<Switch?>>,
        gems: Array<Array<Gem?>>,
        beepers: Array<Array<Beeper?>>,
        portals: Array<Array<Portal?>>,
        platforms: Array<Array<Platform?>>,
    ): Tiles {
        return zipNine2DArrayToObjArrayWith(tiles, levels, colors, biomes, switches, gems, beepers, portals, platforms,
            with = { a: Layout, b: Int, c: Color, d: Biomes, e: Switch?, f: Gem?, g: Beeper?, h: Portal?, i: Platform? -> Tile(
                layout = a,
                blocked = mutableListOf(),
                level = b,
                color = c,
                biome = d,
                switch = e,
                gem = f,
                beeper = g,
                portal = h,
                platform = i,
                players = mutableListOf(),
                changes = null,
            ) }
        )
    }

    private fun convertItemLayoutToSwitches(array: Array<Array<Array<Item?>>>): Array<Array<Switch?>> {
        return array.map { it.map {
            it.firstOrNull { it is Switch? } as Switch?
        }.toTypedArray() }.toTypedArray()
    }

    private fun convertItemLayoutToGems(array: Array<Array<Array<Item?>>>): Array<Array<Gem?>> {
        return array.map { it.map {
            it.firstOrNull { it is Gem? } as Gem?
        }.toTypedArray() }.toTypedArray()
    }

    private fun convertItemLayoutToBeepers(array: Array<Array<Array<Item?>>>): Array<Array<Beeper?>> {
        return array.map { it.map {
            it.firstOrNull { it is Beeper? } as Beeper?
        }.toTypedArray() }.toTypedArray()
    }

    private fun convertItemLayoutToPortals(array: Array<Array<Array<Item?>>>): Array<Array<Portal?>> {
        return array.map { it.map {
            it.firstOrNull { it is Portal? } as Portal?
        }.toTypedArray() }.toTypedArray()
    }

    private fun convertItemLayoutToPlatform(array: Array<Array<Array<Item?>>>): Array<Array<Platform?>> {
        return array.map { it.map {
            it.firstOrNull { it is Platform? } as Platform?
        }.toTypedArray() }.toTypedArray()
    }

    private fun convertItemArrayToLayout(
        itemArray: Array<Array<Array<ItemEnum>>>,
        gemDisappearIn: Int,
        beeperDisappearIn: Int,
        portalList: Array<Portal>,
        platformDataList: Array<PlatformData>,
        platformChangeList: Array<ChangePlatform>
    ): Array<Array<Array<Item?>>> {
        return itemArray.mapIndexed { i, line ->
            line.mapIndexed { j, item ->
                val items = item.map {
                    convertAnItemEnumToItem(it, gemDisappearIn, beeperDisappearIn, portalList, platformDataList, platformChangeList, i, j) }
                items.toTypedArray()
            }.toTypedArray()
        }.toTypedArray()
    }

    private fun convertAnItemEnumToItem(
        item: ItemEnum,
        gemDisappearIn: Int,
        beeperDisappearIn: Int,
        portalList: Array<Portal>,
        platformDataList: Array<PlatformData>,
        platformChangeList: Array<ChangePlatform>,
        i: Int, j: Int
    ): Item? {
        return when (item) {
            ItemEnum.NONE -> null
            ItemEnum.CLOSEDSWITCH -> Switch(on = false)
            ItemEnum.OPENEDSWITCH -> Switch(on = true)
            ItemEnum.GEM -> Gem(gemDisappearIn)
            ItemEnum.BEEPER -> Beeper(beeperDisappearIn)
            ItemEnum.PORTAL ->
                getPortalInListByCoo(portalList, Coordinate(j, i))
            ItemEnum.PLATFORM ->
                getPlatformInListByCoo(platformDataList, platformChangeList, Coordinate(j, i))
        }
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

    private fun convertJsonToLevelLayout(array: Array<Array<String>>, using: String): Array<Array<Int>> {
        return when (using) {
            "colorful" -> array.map { it.map { 1 }.toTypedArray() }.toTypedArray()
            "mountainous", "creative" -> array.map { it.map { it.toIntOrNull() ?: 1 }.toTypedArray() }.toTypedArray()
            else -> throw Exception("Unsupported game module")
        }
    }

    private fun convertJsonToColorLayout(array: Array<Array<String>>, using: String): Array<Array<Color>> {
        return when (using) {
            "colorful" -> array.map { it.map { convertDataToColor(it) }.toTypedArray() }.toTypedArray()
            "mountainous", "creative" -> array.map { it.map { Color.WHITE }.toTypedArray() }.toTypedArray()
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