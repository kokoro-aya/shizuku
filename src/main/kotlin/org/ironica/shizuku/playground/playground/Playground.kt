package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.data.Lock
import org.ironica.shizuku.playground.data.Portal
import org.ironica.shizuku.playground.data.Tiles
import org.ironica.shizuku.runner.GemOrBeeper
import org.ironica.shizuku.runner.initrules.playgroundrules.*
import org.ironica.shizuku.runner.initrules.specialrules.SpecialMessage

class Playground(
    val tiles: Tiles,
    val portals: MutableList<Portal>,
    val locks: List<Lock>,
    val players: MutableList<Player>,

    val additionalGems: MutableList<GemOrBeeper>,
    val additionalBeepers: MutableList<GemOrBeeper>,

    randomInitGems: Int,
    randomInitPortals: Int,
    randomInitBeepers: Int,

    val userCollision: Boolean,
    val maxTerrainHeight: Int,
    val canStackOnTerrain: Boolean,
    val canPutBlockOnVoid: Boolean,

    val eraseTerrainCondition: EraseTerrainCondition,
    val winCondition: WinCondition,
    val loseCondition: LoseCondition,

    stamina: StaminaRules,

    val swimRules: SwimRules,
    val lavaRules: LavaRules,
    val beeperRules: GemOrBeeperRule,
    val gemStaminaRules: GemStaminaRule,

    val specialMessages: List<SpecialMessage>,
) {
}