package org.ironica.shizuku.runner

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.Role

@Serializable
data class PlayerData(val id: Int,
                      val x: Int,
                      val y: Int,
                      val dir: Direction,
                      val role: Role,
                      val stamina: Int)
