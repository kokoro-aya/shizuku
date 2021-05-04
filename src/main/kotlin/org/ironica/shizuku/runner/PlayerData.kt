package org.ironica.shizuku.runner

import kotlinx.serialization.Serializable

@Serializable
data class PlayerData(val id: Int,
                      val x: Int,
                      val y: Int,
                      val dir: String,
                      val role: String,
                      val stamina: String)
