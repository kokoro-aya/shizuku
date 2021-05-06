package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Role
import org.ironica.shizuku.playground.characters.AbstractPlayer

data class PlayerObject(val role: Role) {
    lateinit var player: AbstractPlayer
}
