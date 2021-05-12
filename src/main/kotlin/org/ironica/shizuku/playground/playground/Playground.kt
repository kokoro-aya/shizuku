package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.bridge.AdditionalGem
import org.ironica.shizuku.bridge.AdditionalGold
import org.ironica.shizuku.bridge.ShopInfo
import org.ironica.shizuku.bridge.initrules.SpecialMessage
import org.ironica.shizuku.bridge.initrules.rules.*
import org.ironica.shizuku.playground.Coordinate
import org.ironica.shizuku.playground.Lock
import org.ironica.shizuku.playground.Square
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.items.Portal
import org.ironica.shizuku.playground.world.AbstractWorld

class Playground(
    val squares: List<List<Square>>,
    val portals: MutableList<Portal>,
    val locks: MutableMap<Lock, Coordinate>,
    val players: MutableMap<Player, Coordinate>,

    var world: AbstractWorld,

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
    val laveRules: LavaRule,
    val goldRule: GemOrGoldRule,
    val gemRules: GemOrGoldRule,

    additionalGemOneAfterAnother: Boolean,
    additionalGoldOneAfterAnother: Boolean,

    val decreaseEachUsageOfPortal: Int,
    val dcreaseEachUsageOfLock: Int,

    val biomeRules: BiomeRule,
    val seasonRules: SeasonRule,
    val villageRules: VillageRule,
    val ruinRules: RuinRule,
    val monsterRules: MonsterRule,

    var currentTurns: Int = 0,
    ) {
    init {

    }
}