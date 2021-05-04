package org.ironica.shizuku.runner

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.data.*
import org.ironica.shizuku.runner.initrules.disabled.DisabledFeature
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
    val layout: Array<Array<ItemEnum>>,
    val misc: Array<Array<String>>,
    val portals: Array<Portal> = arrayOf(),
    val locks: Array<Lock> = arrayOf(),
    val players: Array<PlayerData>,
    val disabledFeature: DisabledFeaturesList = arrayOf(),
    val preInitializedObjects: PreInitializedList = arrayOf(
        PreInitializedObject(name = "world", type = "World", param = mapOf())
    ),
    val rules: Rules = Rules(),
    val stairs: Array<Stair> = arrayOf(),
    val platforms: Array<PlatformData> = arrayOf(),
    val additionalGems: Array<GemOrBeeper> = arrayOf(),
    val additionalBeepers: Array<GemOrBeeper> = arrayOf(),
    val specialRules: SpecialRules = SpecialRules(),
)
