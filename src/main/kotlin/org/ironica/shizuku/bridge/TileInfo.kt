package org.ironica.shizuku.bridge

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Coordinate
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.Size

@Serializable
data class TileInfo(
    val shelters: ShelterInfo,
    val villages: VillageInfo,
    val stairs: StairInfo,
    val locks: LockInfo,
    val monsters: MonsterInfo,
)

@Serializable
data class ShelterInfo(
    val coo: Coordinate, val cap: Int,
)

@Serializable
data class VillageInfo(
    val coo: Coordinate, val size: Size,
)

@Serializable
data class StairInfo(
    val coo: Coordinate, val dir: Direction,
)

@Serializable
data class LockInfo(
    val coo: Coordinate, val controlled: Array<Coordinate>,
)

@Serializable
data class MonsterInfo(
    val coo: Coordinate, val stamina: Int, val atk: Int, val level: Int,
)

