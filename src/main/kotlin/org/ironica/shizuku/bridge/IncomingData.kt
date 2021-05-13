package org.ironica.shizuku.bridge

import kotlinx.serialization.Serializable
import org.ironica.shizuku.bridge.initrules.SpecialRules
import org.ironica.shizuku.bridge.initrules.disabled.DisabledFeature
import org.ironica.shizuku.bridge.initrules.preinitialized.PreInitializedObject
import org.ironica.shizuku.bridge.initrules.rules.*
import org.ironica.shizuku.playground.Biome
import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.Season

@Serializable
data class IncomingData(
    val type: String,
    val code: String,
    val grids: List<List<Block>>,
    val levels: List<List<Int>> = List(grids.size) { List(grids[0].size) { 1 } },
    val colors: List<List<Color>> = List(grids.size) { List(grids[0].size) { Color.WHITE } },
    val blockedDirInfo: List<BlockedDirInfo>,
    val tileInfo: TileInfo = TileInfo(
        locks = listOf(), monsters = listOf(), shelters = listOf(), villages = listOf(), stairs = listOf(),
    ),
    val itemInfo: ItemInfo = ItemInfo(
        switches = listOf(), gems = listOf(), golds = listOf(),
        portions = listOf(), portals = listOf(), platforms = listOf(),
    ),
    val biomes: List<List<Biome>> = List(grids.size) { List(grids[0].size) { Biome.PLAINS } },
    val players: List<PlayerInfo>,

    val disabled: List<DisabledFeature> = listOf(),
    val preInitialized: List<PreInitializedObject> = listOf(),
    val rules: Rules = Rules(
        userCollision = true,
        maxTerrainHeight = 20,
        canStackOnTerrain = false,
        canPutBlockOnVoid = false,
        eraseTerrain = EraseTerrainRule(
            canErase = false, minLevelToErase = 1,
        ),
        winCondition = WinConditionRule(
            hasGemMoreThan = 0, hasGoldMoreThanCollected = 0, hasGoldMoreThanSpent = 0,
            noGemLeftOnGround = true, noGoldLeftOnGround = true,
            allSwitches = 2,
            afterTurns = 1000,
            monsters = MonstersCondition(true, 0),
            satisfiedConditions = 2,
        ),
        loseCondition = LoseConditionRule(
            afterTurns = 0, onePlayerKilled = false, oneSpecialistKilled = false,
            allPlayerKilled = true, allSpecialistKilled = true, allPlayerOrSpecialistKilled = true,
            gemsOnGroundAfter = 0,
            switchesLeft = SwitchesLeftCondition(on = true, after = 1000),
            goldLeft = GoldLeftCondition(inBag = false, after = 1000),
        ),
        staminaRules = StaminaRule(
            initial = 10000, max = 15000, consumePerTurn = -10,
            inForest = -20, onHill = -25, onStone = -45, inRuin = -45,
            againstMonster = -35, inShelter = 250, inVillage = 1000,
            golds = GoldStaminaRule(take = -15, drop = -10, inBag = InBagStaminaRule(perItem = -1, perTurn = -10)),
            gems = GemStaminaRule(take = -40, inBag = InBagStaminaRule(perItem = -2, perTurn = -15)),
            portions = PortionStaminaRule(small = 250, medium = 750, large = 2500),
            switches = SwitchesStaminaRule(open = -50, close = -40),
            turnAround = -25, moveForward = -45,
            portalTeleport = -125, changeColor = -0,
            turnLock = TurnLockOrStepStairStaminaRule(up = -80, down = -40),
            jump = -60,
            inLava = InLavaOrWaterStaminaRule(jumpInto = -120, perTurn = -2500, climbUp = -450),
            inWater = InLavaOrWaterStaminaRule(jumpInto = -75, perTurn = -125, climbUp = -250),
            stair = TurnLockOrStepStairStaminaRule(up = -50, down = -35),
        ),
        swimRules = SwimRule(
            canSwim = true, jumpFromLevelMax = 5, climbUpToLevelMax = 3, dieAfterTurns = 0,
        ),
        lavaRules = LavaRule(
            canJumpInto = false, dieAfterTurns = 1, coolDown = 20, willDisappear = false,
        ),
        goldRules = GemOrGoldRule(autoFail = false),
        gemRules = GemOrGoldRule(autoFail = false),
        additionalGemOneAfterAnother = false,
        additionalGoldOneAfterAnother = false,
        portalRules = PortalOrLockRule(defaultEnergy = 15, decreaseEachUsage = 1),
        lockRules = PortalOrLockRule(defaultEnergy = 15, decreaseEachUsage = 1),
        biomeRules = BiomeRule(
            normalCoef = 1.0,
            coldCoef = 1.25,
            hotCoef = 1.5,
            veryColdCoef = 1.75,
            veryHotCoef = 4.5,
        ),
        seasonRules = SeasonRule(
            hasWinter = true,
            winterDuration = 25,
            summerDuration = 45,
            shelter = ShelterRule(
                coef = 0.5,
                maxCount = 5,
                maxPlayersPerShelter = 3,
                extendCapacity = 2,
                extendCount = 3,
            ),
            startWith = Season.SUMMER,
        ),
        villageRules = VillageRule(
            smallCoef = 1.0,
            mediumCoef = 1.25,
            largeCoef = 1.5,
        ),
        ruinRules = RuinRule(
            gainStamina = 500,
            loseStamina = 500,
            getGem = 3,
            loseGem = 2,
            getGold = 10,
            loseGold = 5,
        ),
        monsterRules = MonsterRule(
            defeatBonus = DefeatBonusRule(
                listOf(1, 1, 1, 1, 2, 2, 2, 3, 5, 6),
                listOf(2, 3, 3, 4, 4, 4, 5, 5, 7, 10),
                listOf(0, 0, 0, 0, 100, 125, 150, 200, 350, 500),
            ),
            rankUp = listOf(45, 90, 150, 225, 290, 400, 550, 700, 850, 1000),
        )
    ),
    val additionalGems: List<AdditionalGem> = listOf(),
    val additionalGolds: List<AdditionalGold> = listOf(),

    val specialRules: SpecialRules = SpecialRules(
        changeBlocks = listOf(), changePlatforms = listOf(),
        randomInitGems = 0, randomInitGolds = 0, randomInitPortals = 0,
        specialMessages = listOf(),
    ),
    val shopItems: ShopInfo = ShopInfo(
        portions = listOf(),
        weapons = listOf(),
    ),
)

