package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.Block.*
import org.ironica.shizuku.playground.Direction.*
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
import kotlin.math.roundToInt
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

    val beeperDisappearAfter: Int,
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
            additionalGems.shuffle()
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
            if (it.block == LAVA) lavas.add(
                Lava(
                j, i, 0
            )
            )
        } }

        players.map { it.key.playground = this }

        worlds.add(World(this))
    }

    private val homes = mutableListOf<Home>()
    private val shelters = mutableListOf<Shelter>()

    private val lavas: MutableList<Lava> = mutableListOf()
    private var season = seasonRules.startWith
    private var inThisSeasonFor: Int = 0

    var status: GameStatus = GameStatus.PENDING

    val playerCount: Int
        get() = players.size

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

    private fun isBlockBlocked(from: Coordinate, to: Coordinate): Boolean {
        val fromObj = tiles[from.y][from.x]
        val toObj = tiles[to.y][to.x]
        if (!isTileAccessible(toObj)) return true
        if (userCollision) return toObj.players.isNotEmpty()
        return if (toObj.misc is MountainMiscInfo) {
            val tm = toObj.misc as MountainMiscInfo
            val tc = fromObj.misc as MountainMiscInfo
            val dif = (tm.level ?: 1) - (tc.level ?: 1)
            dif.let {
                dif > 1 || dif < -1 || (dif == 1 && !hasStairToward(from, to))
                        || (dif == -1 && !hasStairToward(to, from))
            }
        } else false
    }

    private fun isAdjacent(from: Coordinate, to: Coordinate): Boolean {
        return from.x == to.x && from.y == to.y - 1
                || from.x == to.x && from.y == to.y + 1
                || from.y == to.y && from.x == to.x - 1
                || from.y == to.y && from.x == to.x + 1
    }

    private fun hasStairToward(from: Coordinate, to: Coordinate): Boolean {
        assert (isAdjacent(from, to))
        val fromObj = tiles[from.y][from.x]
        if (from.y + 1 == to.y) return fromObj.stairs.contains(DOWN)
        if (from.y - 1 == to.y) return fromObj.stairs.contains(UP)
        if (from.x - 1 == to.x) return fromObj.stairs.contains(LEFT)
        if (from.x + 1 == to.x) return fromObj.stairs.contains(RIGHT)
        return false
    }

    private fun isBlockedYPlus(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        if (y == 0) return true
        return isBlockBlocked(Coordinate(x, y), Coordinate(x, y - 1))
    }
    private fun isBlockedYMinus(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        if (y == tiles.size - 1) return true
        return isBlockBlocked(Coordinate(x, y), Coordinate(x, y + 1))
    }
    private fun isBlockedXMinus(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        if (x == 0) return true
        return isBlockBlocked(Coordinate(x, y), Coordinate(x - 1, y))
    }
    private fun isBlockedXPlus(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        if (x == tiles[0].size - 1) return true
        return isBlockBlocked(Coordinate(x, y), Coordinate(x + 1, y))
    }

    fun playerTurnLeft(player: Player): Boolean {
        player.dir = when (player.dir) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
        }
        val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
        player.stamina -= (staminaRule.turnAround * amplitude).roundToInt()
        incrementATurn()
        return true
    }
    fun playerTurnRight(player: Player): Boolean {
        player.dir = when (player.dir) {
            UP -> RIGHT
            LEFT -> UP
            DOWN -> LEFT
            RIGHT -> DOWN
        }
        val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
        player.stamina -= (staminaRule.turnAround * amplitude).roundToInt()
        incrementATurn()
        return true
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
            with (Pair(staminaRule.beeper.inBag, staminaRule.gem.inBag)) {
                it.stamina -= it.beeperInBag * this.first.perTurn * this.first.perItem
                + it.collectedGems * this.second.perItem * this.second.perTurn
            }
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
            if (playerIsInVoid(it)) {
                it.stamina = -1
            }
        }

        // DieCondition (swim, lava, stamina)
        players.keys.filter { it.stamina <= 0 }.forEach {
            playerKill(it)
        }


        // HomeDisappear and HomeCoolDown
        homes.filter { it.destroyed }.forEach {
            tiles[it.y][it.x].block = OPEN
            homes.remove(it)
        }
        homes.forEach { it.decrementCoolDown() }

        // LavaDisappear
        lavas.filter { it.existsFor == lavaRules.coolDown }.forEach {
            with (tiles[it.y][it.x]) {
                if (lavaRules.willDisappear) this.block = OPEN else this.block = STONE
            }
            lavas.remove(it)
        }
        lavas.forEach { it.existsFor += 1 }

        tiles.forEachIndexed { i, it -> it.forEachIndexed { j, it ->
            // Terrain(Block) Changes and Platform Changes
            if (it.changes?.get(0)?.first == currentTurns) {
                if (it.block == LAVA) lavas.removeIf { it.x == j && it.y == i }
                with (it.changes?.get(0)?.second!!) {
                    it.block = this
                    if (this == LAVA) lavas.add(Lava(j, i, existsFor = 0))
                }
            }
            with (it.layout) {
                if (this is Platform && this.changes?.get(0)?.first == currentTurns)
                    this.level = this.changes?.get(0)?.second!!
                // GemDisappear
                if (this is Gem && this.disappearIn == currentTurns) {
                    it.layout = None
                    if (autoFailRule.gemDisappear) status = GameStatus.LOST
                }
                // BeeperDisappear
                if (this is Beeper && this.disappearIn == currentTurns) {
                    it.layout = None
                    if (autoFailRule.beeperDisappear) status = GameStatus.LOST
                }
            }
        } }

        // New Gems and New Beepers
        additionalGems.filter { it.appearIn == currentTurns }.forEach {
            assert (tiles[it.coo.y][it.coo.x].layout is None)
            tiles[it.coo.y][it.coo.x].layout = Gem(it.disappearIn)
            additionalGems.remove(it)
        }
        additionalBeepers.filter { it.appearIn == currentTurns }.forEach {
            assert (tiles[it.coo.y][it.coo.x].layout is None)
            tiles[it.coo.y][it.coo.x].layout = Beeper(it.disappearIn)
            additionalBeepers.remove(it)
        }

        // Winter
        when (season) {
            Season.SUMMER -> if (inThisSeasonFor == seasonRules.summerDuration) {
                season = Season.WINTER
                inThisSeasonFor = 0
            } else inThisSeasonFor ++
            Season.WINTER -> if (inThisSeasonFor == seasonRules.winterDuration) {
                season = Season.SUMMER
                inThisSeasonFor = 0
            } else inThisSeasonFor ++
        }

        if (gameIsWin()) status = GameStatus.WIN
        if (gameIsLost()) status = GameStatus.LOST

        if (status != GameStatus.LOST) currentTurns += 1
    }

    private fun getAmplitudeForPlayer(player: Player): Double {
        return if (shelters.any { it.hasPlayer(player) }) seasonRules.amplitude else seasonRules.amplitude * seasonRules.amplitudeWithoutShelter
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
        if (loseCondition.onePlayerKilled && p.filter { it.javaClass == Player::class.java }.any { it.isDead }) return true
        if (loseCondition.oneSpecialistKilled && p.filterIsInstance<Specialist>().any { it.isDead }) return true
        if (loseCondition.allPlayerKilled && p.filter { it.javaClass == Player::class.java }.all { it.isDead }) return true
        if (loseCondition.allSpecialistKilled && p.filterIsInstance<Specialist>().all { it.isDead }) return true
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
            if (n.after in 1..currentTurns && tiles.any{ it.any { with(it.layout) { this is Switch && if (n.on) !this.on else this.on } } }) return true
        }
        return false
    }

    private fun afterPlayerMakesAMove(player: Player, oldCoo: Coordinate, newCoo: Coordinate) {
        with (shelters.firstOrNull { it.x == oldCoo.x && it.y == oldCoo.y }) {
            if (this?.hasPlayer(player) == true) this.leaveAPlayer(player)
        }
        with (homes.firstOrNull { it.x == oldCoo.x && it.y == oldCoo.y }) {
            if (this?.content == player) this.leaveHome(player)
        }
        with (shelters.firstOrNull { it.x == newCoo.x && it.y == newCoo.y }) {
            this?.joinAPlayer(player)
        }
        with (homes.firstOrNull { it.x == newCoo.x && it.y == newCoo.y }) {
            this?.goHome(player)
        }
        if (player.inLaveForTurns > 0 && !playerIsInLava(player)) player.inLaveForTurns = 0
        if (player.inWaterForTurns > 0 && !playerIsInWater(player)) player.inWaterForTurns = 0
    }

    fun playerMoveForward(player: Player): Boolean {
        val oldCoo = getCooFor(player)
        if (!playerIsBlocked(player)) {
            when(player.dir) {
                UP -> movePlayerUp(player)
                DOWN -> movePlayerDown(player)
                LEFT -> movePlayerLeft(player)
                RIGHT -> movePlayerRight(player)
            }
        }
        val newCoo = getCooFor(player)
        afterPlayerMakesAMove(player, oldCoo, newCoo)
        val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
        player.stamina -= (staminaRule.moveForward * amplitude).roundToInt()
        incrementATurn()
        return true
    }
    fun playerCollectGem(player: Player): Boolean {
        return if (playerIsOnGem(player)) {
            player.collectedGems += 1
            with (players[player]!!) {
                tiles[this.y][this.x].layout = None
            }
            if (additionalGems.size > 0 && additionalGems[0].appearIn == -1) {
                with (additionalGems[0]) {
                    tiles[this.coo.y][this.coo.x].layout = Gem(this.disappearIn)
                }
                additionalGems.removeAt(0)
            }
            val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
            player.stamina -= (staminaRule.gem.take * amplitude).roundToInt()
            incrementATurn()
            true
        } else {
            incrementATurn()
            false
        }
    }
    fun playerToggleSwitch(player: Player): Boolean {
        return when {
            playerIsOnOpenedSwitch(player) -> {
                with (players[player]!!) {
                    with (tiles[this.y][this.x].layout) {
                        this as Switch
                        this.on = false
                    }
                }
                val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
                player.stamina -= (staminaRule.switch.close * amplitude).roundToInt()
                true
            }
            playerIsOnClosedSwitch(player) -> {
                with (players[player]!!) {
                    with (tiles[this.y][this.x].layout) {
                        this as Switch
                        this.on = true
                    }
                }
                val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
                player.stamina -= (staminaRule.switch.open * amplitude).roundToInt()
                true
            }
            else -> {
                incrementATurn()
                false
            }
        }
    }
    fun playerTakeBeeper(player: Player): Boolean {
        return if (playerIsOnBeeper(player)) {
            with (players[player]!!) {
                tiles[this.y][this.x].layout = None
                player.beeperInBag += 1
            }
            val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
            player.stamina -= (staminaRule.beeper.take * amplitude).roundToInt()
            incrementATurn()
            true
        } else {
            incrementATurn()
            false
        }
    }
    fun playerDropBeeper(player: Player): Boolean {
        with (players[player]!!) {
            with (tiles[this.y][this.x]) {
                return if (this.layout == None) {
                    this.layout = Beeper(beeperDisappearAfter + currentTurns)
                    player.beeperInBag += 1
                    val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
                    player.stamina -= (staminaRule.beeper.take * amplitude).roundToInt()
                    incrementATurn()
                    true
                } else {
                    incrementATurn()
                    false
                }
            }
        }
    }
    fun playerKill(player: Player): Boolean {
        return if (players.contains(player)) {
            with(players[player]!!) { tiles[this.y][this.x].players.remove(player) }
            shelters.first { it.hasPlayer(player) }.leaveAPlayer(player)
            homes.first { it.content == player }.leaveHome(player)
            players.remove(player)
            incrementATurn()
            true
        } else {
            incrementATurn()
            false
        }
    }
    fun playerSetUpShelter(player: Player): Boolean {
        val (x, y) = getCooFor(player)
        return if (shelters.none { x == it.x && y == it.y } && shelters.size < seasonRules.shelterMaxCount) {
            val sh = Shelter(x, y, seasonRules.maxPlayerPerShelter)
            sh.joinAPlayer(player)
            shelters.add(sh)
            incrementATurn()
            true
        } else {
            incrementATurn()
            false
        }
    }
    fun playerIsOnGem(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].layout is Gem
    }
    fun playerIsOnOpenedSwitch(player: Player): Boolean {
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
    fun playerIsInVoid(player: Player): Boolean {
        val (x, y) = getCooFor(player).toPairXY()
        return tiles[y][x].block == NONE
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
        with (players[player]!!) {
            with (tiles[this.y][this.x]) {
                return if (this.misc is ColorfulMiscInfo) {
                    (this.misc as ColorfulMiscInfo).color = color
                    val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
                    player.stamina -= (staminaRule.changeColor * amplitude).roundToInt()
                    incrementATurn()
                    true
                } else {
                    incrementATurn()
                    false
                }
            }
        }
    }
    fun playerJump(player: Player): Boolean {
        val oldCoo = getCooFor(player)
        if (isTileAccessible(tiles[oldCoo.y][oldCoo.x])) {
            when (player.dir) {
                UP -> movePlayerUp(player)
                DOWN -> movePlayerDown(player)
                LEFT -> movePlayerLeft(player)
                RIGHT -> movePlayerRight(player)
            }
        }
        val newCoo = getCooFor(player)
        afterPlayerMakesAMove(player, oldCoo, newCoo)
        val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
        player.stamina -= (staminaRule.jump * amplitude).roundToInt()
        incrementATurn()
        return true
    }

    fun playerStepIntoPortal(player: Player): Boolean {
        if (playerIsOnPortal(player)) {
            val p = portals.filter { it.coo == getCooFor(player) }[0]
            if (p.isActive) {
                tiles[p.coo.y][p.coo.x].players.remove(player)
                tiles[p.dest.y][p.dest.x].players.add(player)
                val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(player) else 1.0
                player.stamina -= (staminaRule.portalTeleport * amplitude).roundToInt()
                p.energy -= decreaseEachUsageOfPortal
                if (p.energy <= 0) p.isActive = false
                return true
            }
        }
        return false
    }

    fun specialistIsBeforeLock(specialist: Specialist): Boolean {
        val (x, y) = getCooFor(specialist)
        return when (specialist.dir) {
            UP -> y >= 1 && tiles[y - 1][x].block == LOCK
            DOWN -> y <= tiles.size - 2 && tiles[y + 1][x].block == LOCK
            LEFT -> x >= 1 && tiles[y][x - 1].block == LOCK
            RIGHT -> x <= tiles[0].size - 2 && tiles[y][x + 1].block == LOCK
        }
    }

    fun specialistTurnLockUp(specialist: Specialist): Boolean {
        if (specialistIsBeforeLock(specialist)) {
            with (locks.firstOrNull { it.coo == getCooFor(specialist) } ?: throw Exception()) {
                if (tiles[0][0].misc is MountainMiscInfo && this.energy > 0) {
                    this.controlled.forEach {
                        with (tiles[it.y][it.x].layout) {
                            if (this is Platform) this.level += 1
                            else throw Exception()
                        }
                    }
                    val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(specialist) else 1.0
                    specialist.stamina -= (staminaRule.turnLock * amplitude).roundToInt()
                    this.energy -= decreaseEachUsageOfLock
                    incrementATurn()
                    return true
                }
            }
        }
        incrementATurn()
        return false
    }
    fun specialistTurnLockDown(specialist: Specialist): Boolean {
        if (specialistIsBeforeLock(specialist)) {
            with (locks.firstOrNull { it.coo == getCooFor(specialist) } ?: throw Exception()) {
                if (tiles[0][0].misc is MountainMiscInfo && this.energy > 0) {
                    this.controlled.forEach {
                        with (tiles[it.y][it.x].layout) {
                            if (this is Platform) this.level -= 1
                            else throw Exception()
                        }
                    }
                    val amplitude = if (season == Season.WINTER) getAmplitudeForPlayer(specialist) else 1.0
                    specialist.stamina -= (staminaRule.turnLock * amplitude).roundToInt()
                    this.energy -= decreaseEachUsageOfLock
                    incrementATurn()
                    return true
                }
            }
        }
        incrementATurn()
        return false
    }
    fun specialistExtendShelter(specialist: Specialist): Boolean {
        if (specialist.extendCount > 0) {
            val (x, y) = getCooFor(specialist)
            with (shelters.firstOrNull { x == it.x && y == it.y }) {
                if (this != null) {
                    this.availableFor += seasonRules.extendCapacity
                    specialist.extendCount -= 1
                    incrementATurn()
                    return true
                }
            }
        }
        incrementATurn()
        return false
    }

    fun worldPlace(player: AbstractPlayer, at: Coordinate): Boolean {
        if (userCollision && tiles[at.y][at.x].players.isNotEmpty()) return false
        player as Player
        player.playground = this
        tiles[at.y][at.x].players.add(player)
        players[player] = at
        return true
    }
    fun worldPlace(item: Item, at: Coordinate): Boolean {
        tiles[at.y][at.x].let {
            if (it.layout != None) return false
            if (it.block == NONE) return false
            it.layout = item
            return true
        }
    }
    fun worldPlace(platform: Platform, at: Coordinate): Boolean {
        tiles[at.y][at.x].let {
            if (it.layout != None) return false
            it.layout = platform
            return true
        }
    }
    fun worldPlace(portal: Portal): Boolean {
        tiles[portal.coo.y][portal.coo.x].let {
            if (it.layout != None) return false
            it.layout = portal
            portals.add(portal)
            return true
        }
    }
    fun worldPlace(stair: Stair): Boolean {
        tiles[stair.coo.y][stair.coo.x].let {
            if (it.block == NONE) return false
            if (it.stairs.contains(stair.dir)) return false
            it.stairs.add(stair.dir)
            return true
        }
    }
    fun worldPlace(block: Block, at: Coordinate): Boolean {
        tiles[at.y][at.x].let {
            when (block) {
                NONE -> {
                    removeEverythingOnTile(at.x, at.y)
                }
                LAVA -> {
                    lavas.add(Lava(at.x, at.y, 0))
                }
                else -> {
                }
            }
            it.block = block
        }
        return true
    }

    private fun removeEverythingOnTile(x: Int, y: Int) {
        assert (x in 0 .. tiles[0].size && y in 0 .. tiles.size)
        tiles[y][x].let {
            when (it.layout) {
                is Platform -> {
                    with (Coordinate(x, y)) {
                        locks.filter { l -> l.controlled.contains(this) }
                            .forEach { l -> l.controlled.remove(this) }
                    }
                }
                is Portal -> {
                    portals.remove(it.layout)
                }
                else -> {}
            }
            it.layout = None
            it.stairs.clear()
        }
        if (y > 1) tiles[y - 1][x].let {
            if (it.stairs.contains(DOWN)) it.stairs.remove(DOWN)
        }
        if (y < tiles.size - 1) tiles[y + 1][x].let {
            if (it.stairs.contains(UP)) it.stairs.remove(UP)
        }
        if (x > 1) tiles[y][x - 1].let {
            if (it.stairs.contains(RIGHT)) it.stairs.remove(RIGHT)
        }
        if (x < tiles[0].size - 1) tiles[y][x + 1].let {
            if (it.stairs.contains(LEFT)) it.stairs.remove(LEFT)
        }
    }

    fun levelDown(at: Coordinate): Boolean {
        assert (tiles[0][0].misc is MountainMiscInfo)
        (tiles[at.y][at.x].misc as MountainMiscInfo).let {
            return if (it.level != null) {
                it.level = it.level?.minus(1)
                true
            } else false
        }
    }
    fun waitATurn(): Boolean {
        incrementATurn()
        return true
    }
    fun setToWin(): Boolean {
        status = GameStatus.WIN
        return true
    }
    fun setToLost(): Boolean {
        status = GameStatus.LOST
        return true
    }

    fun getHeight(x: Int, y: Int): Int {
        return tiles[y][x].misc.let {
            if (it is MountainMiscInfo) it.level ?: 1 else 1
        }
    }
}