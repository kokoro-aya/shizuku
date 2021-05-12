package org.ironica.shizuku.bridge

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.ironica.shizuku.bridge.initrules.ChangeBlock
import org.ironica.shizuku.bridge.initrules.ChangePlatform
import org.ironica.shizuku.bridge.initrules.SpecialRules
import org.ironica.shizuku.bridge.initrules.disabled.DisabledFeature
import org.ironica.shizuku.bridge.initrules.preinitialized.PreInitializedObject
import org.ironica.shizuku.bridge.initrules.rules.Rules
import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.items.*
import org.ironica.shizuku.playground.playground.Playground
import org.ironica.shizuku.playground.shop.Weapon
import org.ironica.shizuku.playground.world.World
import yukiLexer
import yukiParser
import java.util.*
import kotlin.Exception

class YukiBridge(
    private val type: String,
    private val code: String,
    private var squares: List<List<Square>> = listOf(),
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
        squares = makeSquares(makeTileGrid(grid, tileInfo), levels, colors, biomes)
        squares
            .injectBlocked(blockedDirInfo)
            .injectItems(itemInfo)
            .injectPlatformChanges(specialRules.changePlatforms)
            .injectBlockChanges(specialRules.changeBlocks)

        squares.forEachIndexed { i, line ->
            line.forEachIndexed { j, loc ->
                loc.portal?.let { portals.add(it) }
                if (loc.tile is Lock) locks[loc.tile as Lock] = Coordinate(j, i)
            }
        }
        val allPlatforms = allCoordinatesOfPlatforms(squares)
        if (locks.map { checkAllControlledByLockAsPlatform(it.key, allPlatforms) }.any { !it })
            throw Exception()
    }

    fun start() {
        val input: CharStream = CharStreams.fromString(code)
        val lexer = yukiLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = yukiParser(tokens)
        val tree: ParseTree = parser.top_level()


        val characters = convertPlayerListToCharacterMap(players, createListOfWeaponsFromShop(shopItems.weapons))

        val playground = Playground(
            squares = squares,
            portals = portals,
            locks = locks,
            characters = characters,
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

        val gameMode = when (type.lowercase(Locale.getDefault())) {
            "colorful" -> GameMode.COLORFUL
            "mountainous" -> GameMode.MOUNTAINOUS
            "creative" -> GameMode.CREATIVE
            "two_dim" -> GameMode.TWO_DIM
            else -> throw Exception()
        }
    }

    private fun convertPlayerListToCharacterMap(players: List<PlayerInfo>, weapons: List<Weapon>): MutableMap<AbstractCharacter, Coordinate> {
        return players.associate { createPlayerInstance(it, weapons) to Coordinate(it.x, it.y) }.toMutableMap()
    }

    private fun createPlayerInstance(player: PlayerInfo, weapons: List<Weapon>): AbstractCharacter {
        return when (player.role) {
            Role.SPECIALIST -> Specialist(player.id, player.dir, player.stamina, player.atk,
                weapons.firstOrNull { it.id == player.weaponId })
            Role.PLAYER -> Player(player.id, player.dir, player.stamina, player.atk,
                weapons.firstOrNull { it.id == player.weaponId })
        }
    }

    private fun createListOfWeaponsFromShop(weapons: List<WeaponShopInfo>): List<Weapon> {
        return weapons.map { Weapon(it.id, it.atk, it.cost) }
    }

    private fun makeTileGrid(
        grid: List<List<Block>>,
        tileInfo: TileInfo,
    ): List<List<Tile>> {
        return grid.mapIndexed { i, line ->
            line.mapIndexed { j, it ->
                when (it) {
                    Block.NONE -> None
                    Block.OPEN -> Open
                    Block.HILL -> Hill
                    Block.STONE -> Stone
                    Block.TREE -> Tree
                    Block.WATER -> Water
                    Block.LAVA -> Lava(0)
                    Block.RUIN -> Ruin
                    Block.SHELTER, Block.VILLAGE, Block.STAIR,
                    Block.LOCK, Block.MONSTER -> setASpecialTile(j, i, tileInfo)
                }
            }
        }
    }

    private fun setASpecialTile(x: Int, y: Int, tiles: TileInfo): Tile {
        val coo = Coordinate(x, y)
        return with (tiles.locks.firstOrNull { it.coo == coo }
            ?: tiles.monsters.firstOrNull { it.coo == coo }
            ?: tiles.shelters.firstOrNull { it.coo == coo }
            ?: tiles.stairs.firstOrNull { it.coo == coo }
            ?: tiles.villages.firstOrNull { it.coo == coo } ?: throw Exception()) {
            setTileSub(this)
        }
    }

    private fun setTileSub(tile: Any): Tile {
        return when (tile) {
            is ShelterInfo -> Shelter(tile.cap)
            is VillageInfo -> Village(tile.size)
            is StairInfo -> Stair(tile.dir)
            is LockInfo -> Lock(tile.controlled.toMutableList(), rules.lockRules.defaultEnergy)
            is MonsterInfo -> Monster(tile.stamina, tile.atk, tile.level,
                rules.monsterRules.defeatBonus.stamina[0],
                rules.monsterRules.defeatBonus.gem[0],
                rules.monsterRules.defeatBonus.gold[0]
                )
            else -> throw Exception()
        }
    }

    private fun makeSquares(
        tiles: List<List<Tile>>,
        levels: List<List<Int>>,
        colors: List<List<Color>>,
        biomes: List<List<Biome>>,
    ): List<List<Square>> {
        return tiles.mapIndexed { i, line ->
            line.mapIndexed { j, it ->
                Square(
                    tile = it,
                    level = levels[i][j],
                    color = colors[i][j],
                    biome = biomes[i][j],
                    switch = null, gem = null, gold = null, portal = null, portion = null, platform = null
                )
            }
        }
    }

    private fun List<List<Square>>.injectBlocked(blockedDirInfo: List<BlockedDirInfo>): List<List<Square>> {
        blockedDirInfo.forEach { this[it.coo.y][it.coo.x].blocked.addAll(it.blocked) }
        return this
    }

    private fun List<List<Square>>.injectItems(items: ItemInfo): List<List<Square>> {
        items.switches.forEach { this[it.coo.y][it.coo.x].switch = Switch(it.on) }
        items.gems.forEach { this[it.coo.y][it.coo.x].gem = Gem(it.disappearIn) }
        items.golds.forEach { this[it.coo.y][it.coo.x].gold = Gold(it.value, it.disappearIn) }
        items.portions.forEach { this[it.coo.y][it.coo.x].portion = Portion(it.cat, it.disappearIn) }
        items.portals.forEach { this[it.coo.y][it.coo.x].portal = Portal(it.coo, it.dest, it.color, it.isActive, rules.portalRules.defaultEnergy) }
        items.platforms.forEach { this[it.coo.y][it.coo.x].platform = Platform(it.level) }
        return this
    }

    private fun List<List<Square>>.injectPlatformChanges(
        changes: List<ChangePlatform>
    ): List<List<Square>> {
        changes.forEach { this[it.coo.y][it.coo.x].platform?.changes?.add(it.inTurn to it.toLevel)
            ?: throw Exception() }
        return this
    }

    private fun List<List<Square>>.injectBlockChanges(
        changes: List<ChangeBlock>
    ): List<List<Square>> {
        changes.forEach { this[it.coo.y][it.coo.x].changes?.add(it.inTurn to it.changeTo)
            ?: throw Exception() }
        return this
    }

    private fun checkAllControlledByLockAsPlatform(lock: Lock, platformList: Array<Coordinate>): Boolean {
        return lock.controlled.all { it ->
            val coo = Coordinate(it.y, it.x)
            platformList.any { it == coo }
        }
    }

    private fun allCoordinatesOfPlatforms(squares: List<List<Square>>): Array<Coordinate> {
        val ans = mutableListOf<Coordinate>()
        for (i in squares.indices) {
            for (j in squares[i].indices) {
                if (squares[i][j].platform != null)
                    ans.add(Coordinate(j, i))
            }
        }
        return ans.toTypedArray()
    }

}