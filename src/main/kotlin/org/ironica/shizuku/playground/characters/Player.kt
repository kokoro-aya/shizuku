package org.ironica.shizuku.playground.characters

import org.ironica.shizuku.playground.Direction
import org.ironica.shizuku.playground.data.Coordinate
import org.ironica.shizuku.playground.playground.Playground

open class Player(
    val id: Int,
    val coo: Coordinate,
    var dir: Direction,
    var stamina: Int
) {
    lateinit var playground: Playground
}
