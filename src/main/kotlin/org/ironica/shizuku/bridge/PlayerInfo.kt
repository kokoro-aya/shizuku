package org.ironica.shizuku.bridge

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.Role

@Serializable
data class PlayerInfo(
    val id: Int,
    val x: Int,
    val y: Int,
    val dir: Direction,
    val role: Role,
    val stamina: Int,
    val atk: Int,
    val weaponId: Int,
    )
