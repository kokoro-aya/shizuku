package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Season

@Serializable
data class Rules(
    val userCollision: Boolean = true,
    val maxTerrainHeight: Int = 15,
    val canStackOnTerrain: Boolean = true,
    val canPutBlockOnVoid: Boolean = true,
    val eraseTerrain: EraseTerrainCondition = EraseTerrainCondition(
        canErase = false, minLevelToErase = 1),
    val winCondition: WinCondition = WinCondition(
        allGemCollected = true, allSwitch = AllSwitchCondition(on = true),
        allBeepersInBag = true, afterTurns = null),
    val loseCondition: LoseCondition = LoseCondition(
        onePlayerKilled = false, oneSpecialistKilled = false,
        allPlayerKilled = false, allSpecialistKilled = false,
        allPlayerOrSpecialistKilled = false, notAllGemCollectedAfter = null,
        notAllSwitchToggledCondition = null, notAllBeeperTakenCondition = null,
        afterTurns = null
    ),
    val staminaRules: StaminaRules = StaminaRules(),
    val swimRules: SwimRules = SwimRules(
        jumpFromLevelMax = 3, climbUpToLevelMax = 3, dieAfterTurns = null
    ),
    val lavaRules: LavaRules = LavaRules(
        canJumpInto = false, dieAfterTurns = null, coolDown = null, willDisappear = false
    ),
    val beeperRules: GemOrBeeperRule = GemOrBeeperRule(disappearAfter = 0, autoFail = false),
    val gemRules: GemOrBeeperRule = GemOrBeeperRule(disappearAfter = 0, autoFail = false),
    val additionalGemOneAfterAnother: Boolean = false,
    val additionalBeeperOneAfterAnother: Boolean = false,
    val portalRules: PortalOrLockRule = PortalOrLockRule(defaultEnergy = 0, decreaseEachUsage = 0),
    val lockRules: PortalOrLockRule = PortalOrLockRule(defaultEnergy = 0, decreaseEachUsage = 0),
    val seasonRules: SeasonRules = SeasonRules(
        hasWinter = false, amplitude = 1.0, winterDuration = 0, summerDuration = 1000,
        shelterMaxCount = 0, maxPlayerPerShelter = 0, amplitudeWithoutShelter = 1.0,
        startWith = Season.SUMMER,
    )
)

