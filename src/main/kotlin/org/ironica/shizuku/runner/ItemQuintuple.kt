package org.ironica.shizuku.runner

import org.ironica.shizuku.playground.tile.*

data class ItemQuintuple(
    val itemArray: Array<Item?>,
    var gem: Gem? = null,
    var beeper: Beeper? = null,
    var switch: Switch? = null,
    var portal: Portal? = null,
    var platform: Platform? = null,
) {
    init {
        itemArray.forEach {
            when (it) {
                is Gem -> if (gem == null) gem = it else throw Exception("Already inited!")
                is Beeper -> if (beeper == null) beeper = it else throw Exception("Already inited!")
                is Switch -> if (switch == null) switch = it else throw Exception("Already inited!")
                is Portal -> if (portal == null) portal = it else throw Exception("Already inited!")
                is Platform -> if (platform == null) platform = it else throw Exception("Already inited!")
                null -> {}
            }
        }
    }
}
