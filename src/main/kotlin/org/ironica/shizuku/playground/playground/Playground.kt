package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.playground.Block
import org.ironica.shizuku.playground.Block.*
import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Direction.*
import org.ironica.shizuku.playground.Role
import org.ironica.shizuku.playground.Season
import org.ironica.shizuku.playground.characters.AbstractPlayer
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.data.*
import org.ironica.shizuku.playground.message.GameStatus
import org.ironica.shizuku.playground.world.World
import org.ironica.shizuku.runner.GemOrBeeper
import org.ironica.shizuku.runner.PlayerData
import org.ironica.shizuku.runner.initrules.playgroundrules.*
import org.ironica.shizuku.runner.initrules.specialrules.SpecialMessage
import kotlin.random.Random

class Playground(
    val tiles: Tiles,
    val portals: MutableList<Portal>,
    val locks: List<Lock>,
    val players: MutableMap<Player, Coordinate> = mutableMapOf(),
    playerDatas: Array<PlayerData>,

    var worlds: MutableList<World> = mutableListOf(),

    val additionalGems: MutableList<GemOrBeeper>,
    val additionalBeepers: MutableList<GemOrBeeper>,

    randomInitGems: Int,
    randomInitBeepers: Int,

    randomInitPortals: Int,

    val userCollision: Boolean,
    val maxTerrainHeight: Int,
    val canStackOnTerrain: Boolean,
    val canPutBlockOnVoid: Boolean,

    val eraseTerrainCondition: EraseTerrainCondition,
    val winCondition: WinCondition,
    val loseCondition: LoseCondition,

    val staminaRule: StaminaRules,

    val swimRules: SwimRules,
    val lavaRules: LavaRules,

    beeperDisappearAfter: Int,
    gemDisappearAfter: Int,
    val autoFailRule: AutoFailRule,

    additionalGemOneAfterAnother: Boolean,
    additionalBeeperOneAfterAnother: Boolean,

    val defaultPortalEnergy: Int,
    defaultLockEnergy: Int,

    val decreaseEachUsageOfPortal: Int,
    val decreaseEachUsageOfLock: Int,

    val seasonRules: SeasonRules,

    val specialMessages: List<SpecialMessage>,

    var currentTurns: Int = 0
) {
    init {
        tiles.forEach { it.forEach {
            when (val x = it.layout) {
                is Gem -> x.disappearIn = gemDisappearAfter
                is Beeper -> x.disappearIn = beeperDisappearAfter
            }
        } }
        portals.forEach { it.energy = defaultPortalEnergy }
        locks.forEach { it.energy = defaultLockEnergy }
        assert (!(randomInitGems > 0 && additionalGemOneAfterAnother))
        assert (!(randomInitBeepers > 0 && additionalBeeperOneAfterAnother))
        if (additionalBeeperOneAfterAnother) {
            additionalBeepers.forEach { it.appearIn = -1 }
        } else {
            additionalBeepers.sortBy { it.appearIn }
            val initialBeepersCount = additionalBeepers.filter { it.appearIn == 0 }.size
            assert (randomInitBeepers in 0 .. initialBeepersCount)
            if (randomInitBeepers > 0) {
                var currentInitialBeepersCount = initialBeepersCount
                for (x in 0 .. initialBeepersCount - randomInitBeepers) {
                    val i = Random.nextInt(from = 0, until = currentInitialBeepersCount)
                    additionalBeepers.removeAt(i)
                    currentInitialBeepersCount--
                }
            }
        }
        if (additionalGemOneAfterAnother) {
            additionalGems.forEach { it.appearIn = -1 }
        } else {
            additionalGems.sortBy { it.appearIn }
            val initialGemsCount = additionalGems.filter { it.appearIn == 0 }.size
            assert (randomInitGems in 0 .. initialGemsCount)
            if (randomInitGems > 0) {
                var currentInitialGemsCount = initialGemsCount
                for (x in 0 .. initialGemsCount - randomInitGems) {
                    val i = Random.nextInt(from = 0, until = currentInitialGemsCount)
                    additionalGems.removeAt(i)
                    currentInitialGemsCount--
                }
            }
        }
        if (randomInitPortals < portals.size) {
            while (portals.size > randomInitPortals) {
                portals.removeAt(Random.nextInt(until = portals.size))
            }
        }
        playerDatas.forEach {
            val player = if (it.role == Role.PLAYER) Player(it.id, it.dir, it.stamina) else Specialist(it.id, it.dir, it.stamina)
            val coo = Coordinate(it.x, it.y)
            players[player] = coo
            if (player is Specialist) player.extendCount = seasonRules.extendCount
            if (userCollision) assert(tiles[it.y][it.x].players.isEmpty())
            tiles[it.y][it.x].players.add(player)
        }

        tiles.forEachIndexed { i, it -> it.forEachIndexed { j, it ->
            if (it.block == HOME) homes.add(Home(
                j, i, staminaRule.home.limit, staminaRule.home.coolDown
            ))
        } }

        players.map { it.key.playground = this }

        worlds.add(World(this))
    }

    val initialSpecialistCount = players.keys.filterIsInstance<Specialist>().size
    val initialPlayerCount = players.size - initialSpecialistCount

    val homes = mutableListOf<Home>()
    val shelters = mutableListOf<Shelter>()
    val season = seasonRules.startWith

    var status: GameStatus = GameStatus.PENDING

    fun win(): Boolean { return status == GameStatus.WIN }
    fun lose(): Boolean { return status == GameStatus.LOST }
    fun pending(): Boolean { return status == GameStatus.PENDING }

    private fun prePrintTile(x: Int, y: Int): String {
        val tile = tiles[y][x]
        if (tile.players.isNotEmpty()) return when (tile.players[0].dir) {
            UP -> "上"
            DOWN -> "下"
            LEFT -> "左"
            RIGHT -> "右"
        }
        if (tile.layout != None) return when (val t = tile.layout) {
            is Gem -> "钻"
            is Switch -> if (t.on) "开" else "关"
            is Beeper -> "器"
            is Portal -> "门"
            is Platform -> "台"
            is None -> throw Exception("This is impossible")
        }
        else return when (tile.block) {
            OPEN -> "空"
            BLOCKED -> "障"
            WATER -> "水"
            TREE -> "林"
            DESERT -> "漠"
            HOME -> "屋"
            MOUNTAIN -> "山"
            STONE -> "石"
            LOCK -> "锁"
            NONE -> "无"
            LAVA -> "火"
        }
    }

    fun printGrid() {
        tiles.forEachIndexed { i, row -> row.forEachIndexed { j, _ ->
                print(prePrintTile(j, i))
            }
            println()
        }
        println()
    }

    private fun getCooFor(player: Player): Coordinate {
        return players[player] ?: throw Exception("Error: No Coordinate found for the querying player")
    }

    private fun isTileAccessible(tile: GridObject): Boolean {
        return when (tile.block) {
            NONE -> false
            OPEN -> true
            BLOCKED -> false
            MOUNTAIN -> false
            STONE -> false
            WATER -> swimRules.canSwim
            LAVA -> lavaRules.canJumpInto
            TREE -> true
            DESERT -> true
            HOME -> true
            LOCK -> false
        }
    }

    private fun isBlockBlocked(from: GridObject, to: GridObject): Boolean {
        if (!isTileAccessible(to)) return true
        if (userCollision) return to.players.isNotEmpty()
        return if (to.misc is MountainMiscInfo) {
            val tm = to.misc as MountainMiscInfo
            val tc = from.misc as MountainMiscInfo
            tm.level != tc.level
        } else false
    }

    private fun isBlockedYPlus(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        if (y == 0) return true
        return isBlockBlocked(tiles[y][x], tiles[y - 1][x])
    }
    private fun isBlockedYMinus(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        if (y == tiles.size - 1) return true
        return isBlockBlocked(tiles[y][x], tiles[y + 1][x])
    }
    private fun isBlockedXMinus(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        if (x == 0) return true
        return isBlockBlocked(tiles[y][x], tiles[y][x - 1])
    }
    private fun isBlockedXPlus(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        if (x == tiles[0].size - 1) return true
        return isBlockBlocked(tiles[y][x], tiles[y][x + 1])
    }

    fun playerTurnLeft(player: Player): Boolean {
        player.dir = when (player.dir) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
        }
    }
    fun playerTurnRight(player: Player): Boolean {
        player.dir = when (player.dir) {
            UP -> RIGHT
            LEFT -> UP
            DOWN -> LEFT
            RIGHT -> DOWN
        }
    }

    private fun movePlayerUp(player: Player) {
        val (x, y) = getCooFor(player).toPairXY()
        tiles[y][x].players.remove(player)
        tiles[y - 1][x].players.add(player)
    }
    private fun movePlayerDown(player: Player) {
        val (x, y) = getCooFor(player).toPairXY()
        tiles[y][x].players.remove(player)
        tiles[y + 1][x].players.add(player)
    }
    private fun movePlayerLeft(player: Player) {
        val (x, y) = getCooFor(player).toPairXY()
        tiles[y][x].players.remove(player)
        tiles[y][x - 1].players.add(player)
    }
    private fun movePlayerRight(player: Player) {
        val (x, y) = getCooFor(player).toPairXY()
        tiles[y][x].players.remove(player)
        tiles[y][x + 1].players.add(player)
    }

    private fun incrementATurn() {
        // Stamina check
        val allPlayers = players.keys
        allPlayers.forEach { it.stamina -= staminaRule.consumePerTurn }
        allPlayers.forEach {
            val bb = staminaRule.beeper.inBag; val bg = staminaRule.gem.inBag
            it.stamina -= it.beeperInBag * bb.perTurn * bb.perItem + it.collectedGems * bg.perItem * bg.perTurn
        }
        allPlayers.forEach {
            if (playerIsInDesert(it)) it.stamina -= staminaRule.inDesert
            if (playerIsInForest(it)) it.stamina -= staminaRule.inForest
            if (playerIsAtHome(it)) it.stamina += staminaRule.home.restore
            if (playerIsInLava(it)) {
                it.stamina -= staminaRule.inLava
                it.inLaveForTurns += 1
            }
            if (playerIsInWater(it)) {
                it.stamina -= staminaRule.swim.perTurn
                it.inWaterForTurns += 1
            }
        }

        // DieCondition (swim, lava, stamina)
        // HomeDisappear
        // LavaDisappear
        // Terrain(Block) Changes
        // Platform Changes
        // GemDisappear and new Gems
        // BeeperDisappear and new Beepers
        // Winter

        if (gameIsWin()) status = GameStatus.WIN
        if (gameIsLost()) status = GameStatus.LOST

        if (status != GameStatus.LOST) currentTurns += 1
    }

    private fun gameIsWin(): Boolean {
        var conditions = 0
        if (winCondition.allGemCollected && tiles.all { it.none { it.layout is Gem } }) conditions += 1
        if (winCondition.allSwitch.on && tiles.all { it.none {
                val l = it.layout
                l is Switch && !l.on}}) conditions += 1
        if (winCondition.allBeepersInBag && tiles.all { it.none { it.layout is Beeper }}) conditions += 1
        if (winCondition.afterTurns in 1..currentTurns) conditions += 1
        return conditions >= winCondition.satisfiedCondition
    }
    private fun gameIsLost(): Boolean {
        val p = players.keys
        if (loseCondition.afterTurns <= currentTurns) return true
        if (loseCondition.onePlayerKilled && p.filterNot { it is Specialist }.size < initialPlayerCount) return true
        if (loseCondition.oneSpecialistKilled && p.filterIsInstance<Specialist>().size < initialSpecialistCount) return true
        if (loseCondition.allPlayerKilled && p.filterNot { it is Specialist }.isEmpty()) return true
        if (loseCondition.allSpecialistKilled && p.filterIsInstance<Specialist>().isEmpty()) return true
        if (loseCondition.allPlayerOrSpecialistKilled && p.isEmpty()) return true
        if (loseCondition.notAllGemCollectedAfter in 1..currentTurns && tiles.any{ it.any { it.layout is Gem }}) return true
        if (loseCondition.notAllBeeperTakenCondition != null) {
            val n = loseCondition.notAllBeeperTakenCondition
            if (n.inBag) {
                if (n.after in 1..currentTurns && tiles.any{ it.any { it.layout is Beeper }}) return true
            } else {
                if (n.after in 1..currentTurns && p.any { it.beeperInBag > 0 }) return true
            }
        }
        if (loseCondition.notAllSwitchToggledCondition != null) {
            val n = loseCondition.notAllSwitchToggledCondition
            if (n.on) {
                if (n.after in 1..currentTurns && tiles.any{ it.any { val l = it.layout; l is Switch && !l.on }}) return true
            } else {
                if (n.after in 1..currentTurns && tiles.any{ it.any { val l = it.layout; l is Switch && l.on }}) return true
            }
        }
        return false
    }

    fun playerMoveForward(player: Player): Boolean {
        if (!playerIsBlocked(player) && player.stamina > 0) {
            when(player.dir) {
                UP -> movePlayerUp(player)
                DOWN -> movePlayerDown(player)
                LEFT -> movePlayerLeft(player)
                RIGHT -> movePlayerRight(player)
            }
        }
        incrementATurn()
        return true
    }
    fun playerCollectGem(player: Player): Boolean {

    }
    fun playerToggleSwitch(player: Player): Boolean {

    }
    fun playerTakeBeeper(player: Player): Boolean {

    }
    fun playerDropBeeper(player: Player): Boolean {

    }
    fun playerKill(player: Player): Boolean {

    }
    fun playerSetUpShelter(player: Player): Boolean {

    }
    fun playerIsOnGem(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].layout is Gem
    }
    fun playerIsOpOpenedSwitch(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        val l = tiles[y][x].layout
        return l is Switch && l.on

    }
    fun playerIsOnClosedSwitch(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        val l = tiles[y][x].layout
        return l is Switch && !l.on

    }
    fun playerIsOnBeeper(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].layout is Beeper
    }
    fun playerIsAtHome(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].block == HOME && homes.any { it.content == player }
    }
    fun playerIsInDesert(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].block == DESERT
    }
    fun playerIsInForest(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].block == TREE
    }
    fun playerIsInWater(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].block == WATER
    }
    fun playerIsInLava(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].block == LAVA
    }
    fun playerIsOnPortal(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].layout is Portal
    }
    fun playerIsBlocked(player: Player): Boolean {
        return when (player.dir) {
            UP -> isBlockedYPlus(player)
            DOWN -> isBlockedYMinus(player)
            LEFT -> isBlockedXMinus(player)
            RIGHT -> isBlockedXPlus(player)
        }
    }
    fun playerIsBlockedLeft(player: Player): Boolean {
        return when (player.dir) {
            RIGHT -> isBlockedYPlus(player)
            LEFT -> isBlockedYMinus(player)
            UP -> isBlockedXMinus(player)
            DOWN -> isBlockedXPlus(player)
        }
    }
    fun playerIsBlockedRight(player: Player): Boolean {
        return when (player.dir) {
            LEFT -> isBlockedYPlus(player)
            RIGHT -> isBlockedYMinus(player)
            DOWN -> isBlockedXMinus(player)
            UP -> isBlockedXPlus(player)
        }
    }
    fun playerIsInWinter(player: Player): Boolean {
        return season == Season.WINTER
    }
    fun playerIsInShelter(player: Player): Boolean {
        return shelters.any { it.hasPlayer(player) }
    }

    fun playerChangeColor(player: Player, color: Color): Boolean {

    }
    fun playerJump(player: Player): Boolean {

    }
    fun specialistTurnLockUp(specialist: Specialist): Boolean {

    }
    fun specialistTurnLockDown(specialist: Specialist): Boolean {

    }
    fun specialistExtendShelter(specialist: Specialist): Boolean {

    }

    fun worldPlace(world: World, player: AbstractPlayer, at: Coordinate) {

    }
    fun worldPlace(world: World, item: Item, at: Coordinate) {

    }
    fun worldPlace(world: World, platform: Platform, at: Coordinate) {

    }
    fun worldPlace(world: World, portal: Portal) {

    }
    fun worldPlace(world: World, stair: Stair) {

    }
    fun worldPlace(world: World, block: Block, at: Coordinate) {

    }

    fun levelDown(world: World, at: Coordinate) {

    }
    fun wait(world: World, turns: Int) {

    }
    fun setToWin(world: World) {

    }
    fun setToLost(world: World) {

    }
}