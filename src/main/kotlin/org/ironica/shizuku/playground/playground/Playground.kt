package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.playground.Block
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.Role
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

    var worlds: List<World>,

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
            if (userCollision) assert(tiles[it.y][it.x].players.isEmpty())
            tiles[it.y][it.x].players.add(player)
        }

        players.map { it.key.playground = this }

        worlds = listOf(World(this))
    }

    val status: GameStatus = GameStatus.PENDING

    fun win(): Boolean { return status == GameStatus.WIN }
    fun lose(): Boolean { return status == GameStatus.LOST }
    fun pending(): Boolean { return status == GameStatus.PENDING }

    private fun prePrintTile(x: Int, y: Int): String {
        val tile = tiles[y][x]
        if (tile.players.isNotEmpty()) return when (tile.players[0].dir) {
            Direction.UP -> "上"
            Direction.DOWN -> "下"
            Direction.LEFT -> "左"
            Direction.RIGHT -> "右"
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
            Block.OPEN -> "空"
            Block.BLOCKED -> "障"
            Block.WATER -> "水"
            Block.TREE -> "林"
            Block.DESERT -> "漠"
            Block.HOME -> "屋"
            Block.MOUNTAIN -> "山"
            Block.STONE -> "石"
            Block.LOCK -> "锁"
            Block.NONE -> "无"
            Block.LAVA -> "火"
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

    fun playerTurnLeft(player: Player): Boolean {

    }
    fun playerTurnRight(player: Player): Boolean {

    }
    fun playerMoveForward(player: Player): Boolean {

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

    }
    fun playerIsOpOpenedSwitch(player: Player): Boolean {

    }
    fun playerIsOnClosedSwitch(player: Player): Boolean {

    }
    fun playerIsOnBeeper(player: Player): Boolean {

    }
    fun playerIsAtHome(player: Player): Boolean {

    }
    fun playerIsInDesert(player: Player): Boolean {

    }
    fun playerIsInForest(player: Player): Boolean {

    }
    fun playerIsInWater(player: Player): Boolean {

    }
    fun playerIsInLava(player: Player): Boolean {

    }
    fun playerIsOnPortal(player: Player): Boolean {

    }
    fun playerIsBlocked(player: Player): Boolean {

    }
    fun playerIsBlockedLeft(player: Player): Boolean {

    }
    fun playerIsBlockedRight(player: Player): Boolean {

    }
    fun playerIsInWinter(player: Player): Boolean {

    }
    fun playerIsInShelter(player: Player): Boolean {

    }

    fun playerChangeColor(player: Player): Boolean {

    }
    fun playerJump(player: Player): Boolean {

    }
    fun specialistTurnLockUp(specialist: Specialist): Boolean {

    }
    fun specialistTurnLockDown(specialist: Specialist): Boolean {

    }
    fun specialistExtendShelter(specialist: Specialist): Boolean {

    }

    fun worldPlace(player: Player, at: Coordinate) {

    }
    fun worldPlace(item: Item, at: Coordinate) {

    }
    fun worldPlace(platform: Platform, at: Coordinate) {

    }
    fun worldPlace(portal: Portal, atStart: Coordinate, atEnd: Coordinate) {

    }
    fun worldPlace(stair: Stair, facing: Direction, at: Coordinate) {

    }
    fun worldPlace(block: Block, at: Coordinate) {

    }

    fun levelDown(at: Coordinate) {

    }
    fun wait(turns: Int) {

    }
    fun setToWin() {

    }
    fun setToLost() {

    }
}