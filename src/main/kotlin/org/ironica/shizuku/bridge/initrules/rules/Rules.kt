package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable

@Serializable
data class Rules(
    val userCollision: Boolean,
    val maxTerrainHeight: Int,
    val canStackOnTerrain: Boolean,
    val canPutBlockOnVoid: Boolean,
    val eraseTerrain: EraseTerrainRule,
    val winCondition: WinConditionRule,
    val loseCondition: LoseConditionRule,
    val staminaRules: StaminaRule,
    val swimRules: SwimRule,
    val lavaRules: LavaRule,
    val goldRules: GemOrGoldRule,
    val gemRules: GemOrGoldRule,
    val additionalGemOneAfterAnother: Boolean,
    val additionalGoldOneAfterAnother: Boolean,
    val portalRules: PortalOrLockRule,
    val lockRules: PortalOrLockRule,
    val biomeRules: BiomeRule,
    val seasonRules: SeasonRule,
    val villageRules: VillageRule,
    val ruinRules: RuinRule,
    val monsterRules: MonsterRule,
)
