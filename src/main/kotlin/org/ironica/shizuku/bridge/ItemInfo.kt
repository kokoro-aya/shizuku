package org.ironica.shizuku.bridge

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Coordinate
import org.ironica.shizuku.playground.Size

@Serializable
data class ItemInfo(
    val switches: Array<SwitchInfo>,
    val gems: Array<GemInfo>,
    val golds: Array<GoldInfo>,
    val portions: Array<PortionInfo>,
    val portals: Array<PortalInfo>,
    val platforms: Array<PlatformInfo>,
)

@Serializable
data class SwitchInfo(
    val coo: Coordinate, val on: Boolean,
)

@Serializable
data class GemInfo(
    val coo: Coordinate, val disappearIn: Int,
)

@Serializable
data class GoldInfo(
    val coo: Coordinate, val value: Int, val disappearIn: Int,
)

@Serializable
data class PortionInfo(
    val coo: Coordinate, val cat: Size,
)

@Serializable
data class PortalInfo(
    val coo: Coordinate, val dest: Coordinate, val isActive: Boolean,
)

@Serializable
data class PlatformInfo(
    val coo: Coordinate, val level: Int,
)