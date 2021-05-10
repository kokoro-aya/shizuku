package org.ironica.shizuku.bridge.initrules.rules

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Season

@Serializable
data class SeasonRule(
    val hasWinter: Boolean,
    val winterDuration: Int,
    val summerDuration: Int,
    val shelter: ShelterRule,
    val startWith: Season,
)


@Serializable
data class ShelterRule(
    val coef: Double,
    val maxCount: Int,
    val maxPlayersPerShelter: Int,
    val extendCapacity: Int,
    val extendCount: Int,
)