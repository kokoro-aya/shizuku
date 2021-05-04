package org.ironica.shizuku.runner.initrules.specialrules

import kotlinx.serialization.Serializable

@Serializable
data class SpecialRules(
    val changeBlocks: Array<ChangeBlock> = arrayOf(),
    val changePlatforms: Array<ChangePlatform> = arrayOf(),
    val specialMessages: Array<SpecialMessage> = arrayOf(),
    val randomInitGem: Int = 0,
    val randomInitPortals: Int = 0,
    val randomInitBeepers: Int = 0,
)
