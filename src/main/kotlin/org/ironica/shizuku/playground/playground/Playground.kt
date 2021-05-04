package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.data.Lock
import org.ironica.shizuku.playground.data.Portal
import org.ironica.shizuku.playground.data.Tiles
import org.ironica.shizuku.runner.GemOrBeeper

class Playground(
    val tiles: Tiles,
    val portals: Array<Portal>,
    val locks: Array<Lock>,
    val players: MutableList<Player>,

    val additionalGems: MutableList<GemOrBeeper>,
    val additionalBeepers: MutableList<GemOrBeeper>,


) {
}