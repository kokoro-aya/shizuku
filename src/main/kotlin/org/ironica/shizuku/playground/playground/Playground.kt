package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.playground.Role
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.data.*
import org.ironica.shizuku.runner.GemOrBeeper
import org.ironica.shizuku.runner.PlayerData
import org.ironica.shizuku.runner.initrules.playgroundrules.*
import org.ironica.shizuku.runner.initrules.specialrules.SpecialMessage
import kotlin.random.Random

class Playground(
    val tiles: Tiles,
    val portals: MutableList<Portal>,
    val locks: List<Lock>,
    val players: MutableMap<Player, Coordinate>,
    playerDatas: MutableList<PlayerData>,

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
    }


}