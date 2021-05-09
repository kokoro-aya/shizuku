package org.ironica.shizuku.runner

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Biomes
import org.ironica.shizuku.playground.tile.*
import org.ironica.shizuku.runner.data.*
import org.ironica.shizuku.runner.initrules.disabled.DisabledFeaturesList
import org.ironica.shizuku.runner.initrules.playgroundrules.Rules
import org.ironica.shizuku.runner.initrules.preinitialized.PreInitializedList
import org.ironica.shizuku.runner.initrules.preinitialized.PreInitializedObject
import org.ironica.shizuku.runner.initrules.specialrules.SpecialRules

@Serializable
data class IncomingData(
    val type: String,
    val code: String,
    val grid: Grid,
    val items: Array<Array<Array<ItemEnum>>>,
    val misc: Array<Array<String>>,
    val portals: Array<Portal> = arrayOf(),
    val locks: Array<LockData> = arrayOf(),
    val players: Array<PlayerData>,

    val disabledFeature: DisabledFeaturesList = arrayOf(),
    val preInitializedObjects: PreInitializedList = arrayOf(
        PreInitializedObject(name = "world", type = "World", param = mapOf())
    ),
    val rules: Rules = Rules(),

    val biomes: Array<Array<Biomes>> = arrayOf(),

    val stairs: Array<StairData> = arrayOf(),
    val platforms: Array<PlatformData> = arrayOf(),
    val ruins: Array<RuinData> = arrayOf(),
    val shelters: Array<ShelterData> = arrayOf(),
    val villages: Array<VillageData> = arrayOf(),

    val additionalGems: Array<GemOrBeeper> = arrayOf(),
    val additionalBeepers: Array<GemOrBeeper> = arrayOf(),
    val specialRules: SpecialRules = SpecialRules(),
)
