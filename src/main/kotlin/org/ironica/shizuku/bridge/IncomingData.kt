package org.ironica.shizuku.bridge

import kotlinx.serialization.Serializable
import org.ironica.shizuku.bridge.initrules.SpecialRules
import org.ironica.shizuku.bridge.initrules.disabled.DisabledFeature
import org.ironica.shizuku.bridge.initrules.preinitialized.PreInitializedObject
import org.ironica.shizuku.bridge.initrules.rules.Rules
import org.ironica.shizuku.playground.Biome

@Serializable
data class IncomingData(
    val type: String,
    val code: String,
    val tiles: Array<Array<Block>>,
    val tileInfo: TileInfo,
    val itemInfo: ItemInfo,
    val biomes: Array<Array<Biome>>,
    val players: Array<PlayerInfo>,

    val disabled: Array<DisabledFeature>,
    val preInitialized: Array<PreInitializedObject>,
    val rules: Rules,
    val additionalGems: Array<AdditionalGem>,
    val additionalGolds: Array<AdditionalGold>,

    val specialRules: SpecialRules,
    val shopItems: ShopInfo,
)

