package org.ironica.shizuku.bridge

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.ironica.shizuku.bridge.initrules.SpecialRules
import org.ironica.shizuku.bridge.initrules.disabled.DisabledFeature
import org.ironica.shizuku.bridge.initrules.preinitialized.PreInitializedObject
import org.ironica.shizuku.bridge.initrules.rules.Rules
import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.items.Portal
import org.ironica.shizuku.playground.playground.Playground
import org.ironica.shizuku.playground.world.World
import yukiLexer
import yukiParser

class YukiBridge(
    private val type: String,
    private val code: String,
    private var tiles: List<List<Tile>> = listOf(),
    grid: List<List<Block>>,
    levels: List<List<Int>>,
    colors: List<List<Color>>,
    blockedDirInfo: List<BlockedDirInfo>,
    tileInfo: TileInfo,
    itemInfo: ItemInfo,
    biomes: List<List<Biome>>,

    private val portals: MutableList<Portal> = mutableListOf(),
    private val locks: MutableMap<Lock, Coordinate> = mutableMapOf(),
    private val players: List<PlayerInfo>,

    private val disabledFeature: List<DisabledFeature>,
    private val preInitialized: List<PreInitializedObject>,

    private val rules: Rules,

    private val additionalGems: List<AdditionalGem>,
    private val additionalGolds: List<AdditionalGold>,

    private val specialRules: SpecialRules,
    private val shopItems: ShopInfo,

    ) {
    init {

    }

    fun start() {
        val input: CharStream = CharStreams.fromString(code)
        val lexer = yukiLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = yukiParser(tokens)
        val tree: ParseTree = parser.top_level()

        val playground = Playground(
            squares =,
            portals =,
            locks =,
            characters =,
            world = mutableListOf(World()),
            additionalGems = additionalGems.toMutableList(),
            additionalGolds = additionalGolds.toMutableList(),
            randomInitGems = specialRules.randomInitGems,
            randomInitGolds = specialRules.randomInitGolds,
            randomInitPortals = specialRules.randomInitPortals,
            specialMessages = specialRules.specialMessages,
            shopItems = shopItems,
            userCollision = rules.userCollision,
            maxTerrainHeight = rules.maxTerrainHeight,
            canStackOnTerrain = rules.canStackOnTerrain,
            canPutBlockOnVoid = rules.canPutBlockOnVoid,
            eraseTerrainCondition = rules.eraseTerrain,
            winCondition = rules.winCondition,
            loseCondition = rules.loseCondition,
            staminaRules = rules.staminaRules,
            swimRules = rules.swimRules,
            lavaRules = rules.lavaRules,
            goldRules = rules.goldRules,
            gemRules = rules.gemRules,
            additionalGemOneAfterAnother = rules.additionalGemOneAfterAnother,
            additionalGoldOneAfterAnother = rules.additionalGoldOneAfterAnother,
            decreaseEachUsageOfPortal = rules.portalRules.decreaseEachUsage,
            decreaseEachUsageOfLock = rules.lockRules.decreaseEachUsage,
            biomeRules = rules.biomeRules,
            seasonRules = rules.seasonRules,
            villageRules = rules.villageRules,
            ruinRules = rules.ruinRules,
            monsterRules = rules.monsterRules,
        )
    }
}