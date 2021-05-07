package org.ironica.shizuku.playground.data

import org.ironica.shizuku.playground.characters.Player

data class Home(
    val x: Int,
    val y: Int,
    val limit: Int,
    val coolDown: Int,
) {
    var used: Int = 0

    var coolDownCounter: Int = 0

    var content: Player? = null

    fun goHome(player: Player): Boolean {
        return if (content == null && coolDownCounter == 0 && used < limit) {
            content = player
            true
        } else false
    }

    fun leaveHome(player: Player): Boolean {
        return if (player == content) {
            content = null
            used += 1
            coolDownCounter = coolDown
            true
        } else false
    }

    fun decrementCoolDown() {
        if (coolDownCounter > 0) coolDownCounter --
    }

    val destroyed: Boolean
        get() = used >= limit
    val vacant: Boolean
        get() = coolDownCounter == 0
}
