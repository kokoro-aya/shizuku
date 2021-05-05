package org.ironica.shizuku.runner.initrules.playgroundrules

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Season

@Serializable
data class SeasonRules(
    val hasWinter: Boolean,
    val amplitude: Double,
    val winterDuration: Int,
    val summerDuration: Int,
    val shelterMaxCount: Int,
    val maxPlayerPerShelter: Int,
    val amplitudeWithoutShelter: Double,
    val startWith: Season,
)
