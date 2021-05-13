package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.bridge.AdditionalGem
import org.ironica.shizuku.bridge.AdditionalGold
import org.ironica.shizuku.bridge.Block
import org.ironica.shizuku.bridge.ShopInfo
import org.ironica.shizuku.bridge.initrules.SpecialMessage
import org.ironica.shizuku.bridge.initrules.rules.*
import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.AbstractCharacter
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.items.Gem
import org.ironica.shizuku.playground.items.Gold
import org.ironica.shizuku.playground.items.Portal
import org.ironica.shizuku.playground.message.GameStatus
import org.ironica.shizuku.playground.world.AbstractWorld
import org.ironica.shizuku.playground.world.World
import org.ironica.shizuku.utils.convertSingleBlockToTile
import kotlin.math.roundToInt
import kotlin.random.Random

class Playground(
    val squares: List<List<Square>>,
    val portals: MutableList<Portal>,
    val locks: MutableMap<Coordinate, Lock>,
    val characters: MutableMap<AbstractCharacter, Coordinate>,

    var world: MutableList<AbstractWorld>,

    val additionalGems: MutableList<AdditionalGem>,
    val additionalGolds: MutableList<AdditionalGold>,

    randomInitGems: Int,
    randomInitGolds: Int,
    randomInitPortals: Int,

    val specialMessages: List<SpecialMessage>,
    val shopItems: ShopInfo,

    val userCollision: Boolean,
    val maxTerrainHeight: Int,
    val canStackOnTerrain: Boolean,
    val canPutBlockOnVoid: Boolean,
    val eraseTerrainCondition: EraseTerrainRule,
    val winCondition: WinConditionRule,
    val loseCondition: LoseConditionRule,

    val staminaRules: StaminaRule,

    val swimRules: SwimRule,
    val lavaRules: LavaRule,
    val goldRules: GemOrGoldRule,
    val gemRules: GemOrGoldRule,

    additionalGemOneAfterAnother: Boolean,
    additionalGoldOneAfterAnother: Boolean,

    val decreaseEachUsageOfPortal: Int,
    val decreaseEachUsageOfLock: Int,

    val biomeRules: BiomeRule,
    val seasonRules: SeasonRule,
    val villageRules: VillageRule,
    val ruinRules: RuinRule,
    val monsterRules: MonsterRule,

    var currentTurns: Int = 0,
    ) {
    init {
        assert (!(randomInitGems > 0 && additionalGemOneAfterAnother))
        assert (!(randomInitGolds > 0 && additionalGoldOneAfterAnother))

        if (additionalGoldOneAfterAnother) {
            additionalGolds.forEach { it.appearIn = -1 }
            additionalGolds.shuffle()
        } else {
            additionalGolds.sortBy { it.appearIn }
            val initialGoldsCount = additionalGolds.filter { it.appearIn == 0 }.size
            assert (randomInitGolds in 0 .. initialGoldsCount)
            if (randomInitGolds > 0) {
                var currentInitialGoldsCount = initialGoldsCount
                for (x in 0 .. initialGoldsCount - randomInitGolds) {
                    val i = Random.nextInt(from = 0, until = currentInitialGoldsCount)
                    additionalGolds.removeAt(i)
                    currentInitialGoldsCount --
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
                    currentInitialGemsCount --
                }
            }
        }
        if (randomInitPortals < portals.size) {
            while (portals.size > randomInitPortals) {
                portals[Random.nextInt(until = portals.size)].isActive = false
            }
        }
        characters.forEach { when (val k = it.key) {
            is Player -> k.playground = this
            is Specialist -> k.playground = this
        } }

        val w = World()
        w.playground = this
        world.add(w)
    }

    private var season = seasonRules.startWith
    private var inThisSeasonFor: Int = 0

    private var allGemCollected: Int = 0
    private var allGoldCollected: Int = 0
    private var allGoldSpent: Int = 0

    private var allMonstersLeft: Int = squares.flatten().filter { it.tile is Monster }.size
    private var killedMonsters: Int = 0

    private var shelterExtendCount = seasonRules.shelter.extendCount
    private var shelterCount: Int = squares.flatten().filter { it.tile is Shelter }.size

    var status: GameStatus = GameStatus.PENDING
    val characterCount: Int
        get() = characters.size
    val aliveCharacterCount: Int
        get() = characters.keys.filter { it.isAlive }.size
    val deadCharacterCount: Int
        get() = characters.keys.filter { it.isDead }.size

    fun win(): Boolean = status == GameStatus.WIN
    fun lose(): Boolean = status == GameStatus.LOST
    fun pending(): Boolean = status == GameStatus.PENDING

    private fun prePrintTile(x: Int, y: Int): String {
        val sq = squares[y][x]
        if (sq.players.isNotEmpty()) return when (sq.players[0].dir) {
            Direction.UP -> "上"
            Direction.DOWN -> "下"
            Direction.LEFT -> "左"
            Direction.RIGHT -> "右"
        }
        if (sq.gem != null) return "钻"
        if (sq.switch != null) return if (sq.switch!!.on) "开" else "关"
        if (sq.gold != null) return "金"
        if (sq.portal != null) return "门"
        if (sq.platform != null) return "台"
        return when (sq.tile) {
            Hill -> "山"
            is Lava -> "火"
            is Lock -> "锁"
            is Monster -> "魔"
            None -> "无"
            Open -> "空"
            Ruin -> "墟"
            is Shelter -> "屋"
            is Stair -> "阶"
            Stone -> "石"
            Tree -> "树"
            is Village -> "村"
            Water -> "水"
        }
    }

    fun printGrid() {
        squares.forEachIndexed { i, row -> row.forEachIndexed { j, _ ->
                print(prePrintTile(j, i))
            }
            println()
        }
        println()
    }

    private fun getCooFor(char: AbstractCharacter): Coordinate {
        return characters[char] ?: throw Exception()
    }

    private fun Coordinate.toSquare(): Square {
        return squares[this.y][this.x]
    }

    private fun isTileAccessible(tile: Tile): Boolean {
        return when (tile) {
            is Lava -> lavaRules.canJumpInto
            Water -> swimRules.canSwim
            is Lock, None, Stone -> false
            else -> true
        }
    }

    private fun isMoveBlocked(from: Coordinate, to: Coordinate): Boolean {
        assert (isAdjacent(from, to))
        val fromSq = squares[from.y][from.x]
        val toSq = squares[to.y][to.x]
        if (!isTileAccessible(toSq.tile)) return true
        if (userCollision) return toSq.players.isNotEmpty()
        return (toSq.level - fromSq.level).let {
                it < -1 || it > 1 || (it == 1 && !hasStairToward(from, to))
                        || (it == -1 && !hasStairToward(to, from))
        }
    }

    private fun isJumpBlocked(from: Coordinate, to: Coordinate): Boolean {
        assert (isAdjacent(from, to))
        val fromSq = squares[from.y][from.x]
        val toSq = squares[to.y][to.x]
        if (!isTileAccessible(toSq.tile)) return true
        if (userCollision) return toSq.players.isNotEmpty()
        return (toSq.level - fromSq.level).let { it < -1 || it > 1 }
    }

    private fun isAdjacent(from: Coordinate, to: Coordinate): Boolean {
        return from.x == to.x && from.y == to.y - 1
                || from.x == to.x && from.y == to.y + 1
                || from.y == to.y && from.x == to.x - 1
                || from.y == to.y && from.x == to.x + 1
    }

    private fun hasStairToward(from: Coordinate, to: Coordinate): Boolean {
        assert (isAdjacent(from, to))
        val toTile = squares[to.y][to.x].tile
        if (toTile !is Stair) return false
        if (from.y + 1 == to.y) return toTile.dir == Direction.UP
        if (from.y - 1 == to.y) return toTile.dir == Direction.DOWN
        if (from.x - 1 == to.x) return toTile.dir == Direction.RIGHT
        if (from.x + 1 == to.x) return toTile.dir == Direction.LEFT
        return false
    }

    private fun isBlockedYPlus(char: AbstractCharacter): Boolean {
        val (x, y) = getCooFor(char).toPairXY()
        if (y == 0) return true
        return isMoveBlocked(Coordinate(x, y), Coordinate(x, y - 1))
    }
    private fun isBlockedYMinus(char: AbstractCharacter): Boolean {
        val (x, y) = getCooFor(char).toPairXY()
        if (y == squares.size - 1) return true
        return isMoveBlocked(Coordinate(x, y), Coordinate(x, y + 1))
    }
    private fun isBlockedXMinus(char: AbstractCharacter): Boolean {
        val (x, y) = getCooFor(char).toPairXY()
        if (x == 0) return true
        return isMoveBlocked(Coordinate(x, y), Coordinate(x - 1, y))
    }
    private fun isBlockedXPlus(char: AbstractCharacter): Boolean {
        val (x, y) = getCooFor(char).toPairXY()
        if (x == squares[0].size - 1) return true
        return isMoveBlocked(Coordinate(x, y), Coordinate(x + 1, y))
    }

    private fun getAmplitudeForCharacter(char: AbstractCharacter): Double {
        val (x, y) = getCooFor(char).toPairXY()
        squares[y][x].let {
            if (it.tile is Shelter) return 0.5
            if (it.tile is Village) return 0.0
            return when (season) {
                Season.SUMMER -> when (it.biome) {
                    Biome.SNOWY -> biomeRules.veryColdCoef
                    Biome.COLD -> biomeRules.coldCoef
                    Biome.VALLEY -> biomeRules.normalCoef
                    Biome.PLAINS -> biomeRules.normalCoef
                    Biome.SWAMP -> biomeRules.hotCoef
                    Biome.DESERT -> biomeRules.hotCoef
                    Biome.BADLANDS -> biomeRules.veryHotCoef
                }
                Season.WINTER -> when (it.biome) {
                    Biome.SNOWY -> biomeRules.veryColdCoef
                    Biome.COLD -> biomeRules.veryColdCoef
                    Biome.VALLEY -> biomeRules.coldCoef
                    Biome.PLAINS -> biomeRules.normalCoef
                    Biome.SWAMP -> biomeRules.normalCoef
                    Biome.DESERT -> biomeRules.hotCoef
                    Biome.BADLANDS ->biomeRules.veryHotCoef
                }
            }
        }
    }

    fun characterTurnLeft(char: AbstractCharacter): Boolean {
        char.dir = when (char.dir) {
            Direction.UP -> Direction.LEFT
            Direction.LEFT -> Direction.DOWN
            Direction.DOWN -> Direction.RIGHT
            Direction.RIGHT -> Direction.UP
        }
        val amplitude = getAmplitudeForCharacter(char)
        char.stamina -= (staminaRules.turnAround * amplitude).roundToInt()
        incrementATurn()
        return true
    }

    fun characterTurnRight(char: AbstractCharacter): Boolean {
        char.dir = when (char.dir) {
            Direction.UP -> Direction.RIGHT
            Direction.LEFT -> Direction.UP
            Direction.DOWN -> Direction.LEFT
            Direction.RIGHT -> Direction.DOWN
        }
        val amplitude = getAmplitudeForCharacter(char)
        char.stamina -= (staminaRules.turnAround * amplitude).roundToInt()
        incrementATurn()
        return true
    }

    private fun movePlayerUp(char: AbstractCharacter) {
        val (x, y) = getCooFor(char).toPairXY()
        squares[y][x].players.remove(char)
        squares[y - 1][x].players.add(char)
    }
    private fun movePlayerDown(char: AbstractCharacter) {
        val (x, y) = getCooFor(char).toPairXY()
        squares[y][x].players.remove(char)
        squares[y + 1][x].players.add(char)
    }
    private fun movePlayerLeft(char: AbstractCharacter) {
        val (x, y) = getCooFor(char).toPairXY()
        squares[y][x].players.remove(char)
        squares[y][x - 1].players.add(char)
    }
    private fun movePlayerRight(char: AbstractCharacter) {
        val (x, y) = getCooFor(char).toPairXY()
        squares[y][x].players.remove(char)
        squares[y][x + 1].players.add(char)
    }

    private fun incrementATurn() {
        // Stamina check
        val allChars = characters.keys
        allChars.forEach { it.stamina -= staminaRules.consumePerTurn }
        allChars.forEach {
            with (Pair(staminaRules.golds.inBag, staminaRules.gems.inBag)) {
                it.stamina += (it.goldsInBag * this.first.perItem * this.first.perTurn
                        + it.collectedGems * this.second.perItem * this.second.perTurn)
            }
        }
        allChars.forEach {
            val amp = getAmplitudeForCharacter(it)
            if (characterIsInForest(it)) it.stamina += (staminaRules.inForest * amp).roundToInt()
            if (characterIsOnHill(it)) it.stamina += (staminaRules.onHill * amp).roundToInt()
            if (characterIsOnStone(it)) it.stamina += (staminaRules.onStone * amp).roundToInt()
            if (characterIsInRuin(it)) it.stamina += (staminaRules.inRuin * amp).roundToInt()
            if (characterIsAgainstMonster(it)) it.stamina += (staminaRules.againstMonster * amp).roundToInt()
            if (characterIsInShelter(it)) it.stamina += (staminaRules.inShelter * amp).roundToInt()
            if (characterIsInVillage(it)) it.stamina += (staminaRules.inVillage * amp).roundToInt()
            if (characterIsInWater(it)) {
                it.stamina += (staminaRules.inWater.perTurn * amp).roundToInt()
                it.inWaterForTurns += 1
            }
            if (characterIsInLava(it)) {
                it.stamina += (staminaRules.inLava.perTurn * amp).roundToInt()
                it.inLavaForTurns += 1
            }
            if (characterIsInVoid(it)) {
                it.stamina = Int.MIN_VALUE / 2
            }
        }

        // DieCondition (swim, lava, stamina)
        allChars.filter { it.stamina <= 0 }.forEach { killACharacter(it) }

        // Lava Disappear
        squares.forEachIndexed  { i, line -> line.forEachIndexed { j, it ->
            with (it.tile) {
                if (this is Lava) {
                    this.lastingFor += 1
                    if (this.lastingFor >= lavaRules.coolDown) {
                        it.tile = if (lavaRules.willDisappear) Open else Stone
                    }
                }
            }
            // Terrain(Block)changes and Platform Changes
            if (it.changes?.get(0)?.first == currentTurns) {
                with (it.changes?.get(0)?.second!!) {
                    it.tile = convertSingleBlockToTile(this, i, j, null, null, null)
                }
            }
            with (it.platform) {
                if (this != null && this.changes?.get(0)?.first == currentTurns) {
                    val target = this.changes?.get(0)?.second!!
                    assert (target in 1 .. maxTerrainHeight)
                    if (target > it.level) this.level = target else throw Exception()
                }
            }
            // Gem / Gold / Portion Disappear
            with (it.gem) {
                if (this != null && this.disappearIn == currentTurns) {
                    it.gem = null
                    if (gemRules.autoFail) status = GameStatus.LOST
                }
            }
            with (it.gold) {
                if (this != null && this.disappearIn == currentTurns) {
                    it.gold = null
                    if (goldRules.autoFail) status = GameStatus.LOST
                }
            }
            with (it.portion) {
                if (this != null && this.disappearIn == currentTurns) it.portion = null
            }
        } }

        // New Gems, and New Golds
        additionalGems.filter { it.appearIn == currentTurns }.forEach {
            squares[it.coo.y][it.coo.x].gem = Gem(it.disappearIn)
            additionalGems.remove(it)
        }
        additionalGolds.filter { it.appearIn == currentTurns }.forEach {
            squares[it.coo.y][it.coo.x].gold = Gold(it.value, it.disappearIn)
            additionalGolds.remove(it)
        }

        // Seasons
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

        if (gameIsLost()) status = GameStatus.LOST
        if (gameIsWin()) status = GameStatus.WIN

        if (status != GameStatus.LOST && status != GameStatus.WIN) currentTurns += 1
    }

    private fun gameIsWin(): Boolean {
        var conditions = 0
        if (winCondition.noGemLeftOnGround && squares.all { it.all { it.gem == null } }) conditions ++
        if (winCondition.noGoldLeftOnGround && squares.all { it.all { it.gem == null } }) conditions ++
        if (winCondition.afterTurns in 1 .. currentTurns) conditions ++
        if (winCondition.allSwitches == 2 && squares.all { it.all {
            it.switch != null && it.switch!!.on || it.switch == null
        } }) conditions ++
        if (winCondition.allSwitches == 1 && squares.all { it.all {
            it.switch != null && !it.switch!!.on || it.switch == null
        } }) conditions ++
        if (winCondition.hasGemMoreThan <= allGemCollected) conditions ++
        if (winCondition.hasGoldMoreThanCollected <= allGoldCollected) conditions ++
        if (winCondition.hasGoldMoreThanSpent <= allGoldSpent) conditions ++
        if (winCondition.monsters.beforeTurns > 0) {
            if (winCondition.monsters.allOrAnyKilled) {
                if (allMonstersLeft == 0) conditions ++
            } else {
                if (killedMonsters > 0) conditions ++
            }
        }
        return conditions >= winCondition.satisfiedConditions
    }

    private fun gameIsLost(): Boolean {
        val p = characters.keys
        if (loseCondition.afterTurns <= currentTurns) return true
        if (loseCondition.onePlayerKilled && p.filter { it.javaClass == Player::class.java }.any { it.isDead }) return true
        if (loseCondition.oneSpecialistKilled && p.filterIsInstance<Specialist>().any { it.isDead }) return true
        if (loseCondition.allPlayerKilled && p.filter { it.javaClass == Player::class.java }.all { it.isDead }) return true
        if (loseCondition.allSpecialistKilled && p.filterIsInstance<Specialist>().all { it.isDead }) return true
        if (loseCondition.allPlayerOrSpecialistKilled && p.isEmpty()) return true
        if (loseCondition.gemsOnGroundAfter in 1 .. currentTurns && squares.any { it.any { it.gem != null } }) return true
        with (loseCondition.goldLeft) {
            if (this.inBag) {
                if (this.after in 1 .. currentTurns && squares.any { it.any { it.gold != null } }) return true
            } else {
                if (this.after in 1 .. currentTurns && p.any { it.goldsInBag > 0 }) return true
            }
        }
        with (loseCondition.switchesLeft) {
            if (this.on) {
                if (this.after in 1 .. currentTurns && squares.any { it.any { it.switch?.on == true } }) return true
            } else {
                if (this.after in 1 .. currentTurns && squares.any { it.any { it.switch?.on == false } }) return true
            }
        }
        return false
    }

    private fun afterPlayerMakesAMove(char: AbstractCharacter, oldTile: Tile, newTile: Tile) {
        if (oldTile is Shelter && oldTile.hasPlayer(char)) oldTile.leaveAplayer(char)
        if (newTile is Shelter && !newTile.hasPlayer(char)) newTile.joinAPlayer(char)
        if (char.inLavaForTurns > 0 && !characterIsInLava(char)) char.inLavaForTurns = 0
        if (char.inWaterForTurns > 0 && !characterIsInWater(char)) char.inWaterForTurns = 0
    }

    fun characterMoveForward(char: AbstractCharacter): Boolean {
        val oldTile = getCooFor(char).toSquare()
        if (!characterIsBlocked(char)) {
            when (char.dir) {
                Direction.UP -> movePlayerUp(char)
                Direction.DOWN -> movePlayerDown(char)
                Direction.LEFT -> movePlayerLeft(char)
                Direction.RIGHT -> movePlayerRight(char)
            }
        }
        val newTile = getCooFor(char).toSquare()
        afterPlayerMakesAMove(char, oldTile.tile, newTile.tile)
        val amp = getAmplitudeForCharacter(char)
        char.stamina += (staminaRules.moveForward * amp).roundToInt()
        incrementATurn()
        return true
    }

    fun characterCollectGem(char: AbstractCharacter): Boolean {
        return if (characterIsOnGem(char)) {
            char.collectedGems += 1
            with (characters[char]!!) {
                this.toSquare().gem = null
            }
            if (additionalGems.size > 0 && additionalGems[0].appearIn == -1) {
                with (additionalGems[0]) {
                    this.coo.toSquare().gem = Gem(this.disappearIn)
                }
                additionalGems.removeAt(0)
            }
            val amp = getAmplitudeForCharacter(char)
            char.stamina += (staminaRules.gems.take * amp).roundToInt()
            allGemCollected ++
            incrementATurn()
            true
        } else {
            incrementATurn()
            false
        }
    }

    fun characterToggleSwitch(char: AbstractCharacter): Boolean {
        return when {
            characterIsOnOpenedSwitch(char) -> {
                with (characters[char]!!) {
                    with (this.toSquare().switch) {
                        this?.on = false
                    }
                }
                val amp = getAmplitudeForCharacter(char)
                char.stamina += (staminaRules.switches.close * amp).roundToInt()
                incrementATurn()
                true
            }
            characterIsOnClosedSwitch(char) -> {
                with (characters[char]!!) {
                    with (this.toSquare().switch) {
                        this?.on = true
                    }
                }
                val amp = getAmplitudeForCharacter(char)
                char.stamina += (staminaRules.switches.close * amp).roundToInt()
                incrementATurn()
                true
            }
            else -> {
                incrementATurn()
                false
            }
        }
    }

    fun characterTakeGold(char: AbstractCharacter): Boolean {
        return if (characterIsOnGold(char)) {
            with (characters[char]!!) {
                val golds = this.toSquare().gold!!.value
                char.collectedGolds += golds
                allGoldCollected += golds
                this.toSquare().gold = null
            }
            val amp = getAmplitudeForCharacter(char)
            char.stamina += (staminaRules.golds.take * amp).roundToInt()
            incrementATurn()
            true
        } else {
            incrementATurn()
            false
        }
    }

    fun characterDropGold(char: AbstractCharacter, value: Int): Boolean {
        if (value > char.goldsInBag) return false
        with (characters[char]!!.toSquare()) {
            return if (this.gold == null) {
                this.gold = Gold(value, 0)
                char.goldsInBag -= value
                val amp = getAmplitudeForCharacter(char)
                char.stamina += (staminaRules.golds.drop * amp).roundToInt()
                incrementATurn()
                true
            } else {
                this.gold = Gold(value + this.gold!!.value, 0)
                char.goldsInBag -= value
                val amp = getAmplitudeForCharacter(char)
                char.stamina += (staminaRules.golds.drop * amp).roundToInt()
                incrementATurn()
                true
            }
        }
    }

    private fun killACharacter(char: AbstractCharacter) {
        with (characters[char]!!.toSquare()) {
            this.players.remove(char)
            if (this.tile is Shelter) (this.tile as Shelter).leaveAplayer(char)
            this.platform?.players?.remove(char)
            char.stamina = Int.MIN_VALUE / 2
            // we won't remove the character from the list
        }
    }

    fun characterKill(char: AbstractCharacter): Boolean {
        return if (characters.contains(char)) {
            killACharacter(char)
            incrementATurn()
            true
        } else {
            incrementATurn()
            false
        }
    }

    fun characterFightAgainstMonster(char: AbstractCharacter): Boolean {

    }

    fun CharacterSetUpShelter(char: AbstractCharacter): Boolean {
        with (getCooFor(char).toSquare()) {
            if (this.tile !is Open || shelterCount >= seasonRules.shelter.maxCount) {
                incrementATurn()
                return false
            } else {
                val sh = Shelter(seasonRules.shelter.maxPlayersPerShelter)
                sh.joinAPlayer(char)
                incrementATurn()
                true
            }
        }
        incrementATurn()
        return false
    }

    fun characterIsOnGem(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().gem != null
    }

    fun characterIsOnOpenedSwitch(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().switch?.on == true
    }

    fun characterIsOnClosedSwitch(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().switch?.on == false
    }

    fun characterIsOnGold(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().gold != null
    }

    fun characterIsOnPortion(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().portion != null
    }

    fun characterIsInVillage(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Village
    }

    fun characterIsInForest(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Tree
    }

    fun characterIsOnHill(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Hill
    }

    fun characterIsOnStone(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Stone
    }

    fun characterIsInVoid(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is None
    }

    fun characterIsInWater(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Water
    }

    fun characterIsInLava(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Lava
    }

    fun characterIsInRuin(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Ruin
    }

    fun characterIsInShelter(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Shelter
    }

    fun characterIsAgainstMonster(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().tile is Monster
    }

    fun characterIsInSnowy(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().biome == Biome.SNOWY
    }

    fun characterIsInCold(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().biome == Biome.COLD
    }

    fun characterIsInValley(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().biome == Biome.VALLEY
    }

    fun characterIsInPlains(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().biome == Biome.PLAINS
    }

    fun characterIsInSwamp(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().biome == Biome.SWAMP
    }

    fun characterIsInDesert(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().biome == Biome.DESERT
    }

    fun characterIsInBadland(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().biome == Biome.BADLANDS
    }

    fun characterIsOnPlatform(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().platform?.players?.contains(char) ?: false
    }

    fun characterIsOnPortal(char: AbstractCharacter): Boolean {
        return getCooFor(char).toSquare().portal != null
    }

    fun characterIsBlocked(char: AbstractCharacter): Boolean {
        return when (char.dir) {
            Direction.UP -> isBlockedYPlus(char)
            Direction.DOWN -> isBlockedYMinus(char)
            Direction.LEFT -> isBlockedXMinus(char)
            Direction.RIGHT -> isBlockedXPlus(char)
        }
    }

    fun characterIsBlockedLeft(char: AbstractCharacter): Boolean {
        return when (char.dir) {
            Direction.RIGHT -> isBlockedYPlus(char)
            Direction.LEFT -> isBlockedYMinus(char)
            Direction.UP -> isBlockedXMinus(char)
            Direction.DOWN -> isBlockedXPlus(char)
        }
    }

    fun characterIsBlockedRight(char: AbstractCharacter): Boolean {
        return when (char.dir) {
            Direction.LEFT -> isBlockedYPlus(char)
            Direction.RIGHT -> isBlockedYMinus(char)
            Direction.DOWN -> isBlockedXMinus(char)
            Direction.UP -> isBlockedXPlus(char)
        }
    }

    fun characterIsInWinter(char: AbstractCharacter): Boolean {
        return season == Season.WINTER
    }

    fun characterChangeColor(char: AbstractCharacter, color: Color): Boolean {
        getCooFor(char).toSquare().color = color
        return true
    }

    fun characterJump(char: AbstractCharacter): Boolean {

    }

    fun characterStepIntoPortal(char: AbstractCharacter): Boolean {

    }

    fun specialistIsBeforeLock(specialist: Specialist): Boolean {
        val (x, y) = getCooFor(specialist)
        return when (specialist.dir) {
            Direction.UP -> y >= 1 && squares[y - 1][x].tile is Lock
            Direction.DOWN -> y <= squares.size - 2 && squares[y + 1][x].tile is Lock
            Direction.LEFT -> x >= 1 && squares[y][x - 1].tile is Lock
            Direction.RIGHT -> x <= squares[0].size - 2 && squares[y][x + 1].tile is Lock
        }
    }

    fun specialistTurnLockUp(specialist: Specialist): Boolean {
        if (specialistIsBeforeLock(specialist)) {
            with (locks[getCooFor(specialist)]) {
                this?.controlled?.forEach {
                    with (it.toSquare().platform) {
                        if (this == null) throw Exception()
                        else this.level += 1
                    }
                }
                this?.energy = this?.energy?.minus(1) ?: 0
            }
            val amp = getAmplitudeForCharacter(specialist)
            specialist.stamina += (staminaRules.turnLock.up * amp).roundToInt()
            incrementATurn()
            return true
        }
        incrementATurn()
        return false
    }

    fun specialistTurnLockDown(specialist: Specialist): Boolean {
        if (specialistIsBeforeLock(specialist)) {
            with (locks[getCooFor(specialist)]) {
                this?.controlled?.forEach {
                    with (it.toSquare().platform) {
                        if (this == null) throw Exception()
                        else {
                            if (it.toSquare().level < this.level - 1)
                                this.level -= 1
                        }
                    }
                }
                this?.energy = this?.energy?.minus(1) ?: 0
            }
            val amp = getAmplitudeForCharacter(specialist)
            specialist.stamina += (staminaRules.turnLock.down * amp).roundToInt()
            incrementATurn()
            return true
        }
        incrementATurn()
        return false
    }

    fun specialistExtendShelter(specialist: Specialist): Boolean {
        if (specialist.extendCount > 0 && shelterExtendCount > 0) {
            with (getCooFor(specialist).toSquare().tile) {
                if (this is Shelter) {
                    this.cap += seasonRules.shelter.extendCapacity
                    specialist.extendCount -= 1
                    shelterExtendCount -= 1
                    incrementATurn()
                    return true
                }
            }
        }
        incrementATurn()
        return false
    }

}