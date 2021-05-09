package org.ironica.shizuku.playground.world

import org.ironica.shizuku.playground.Block
import org.ironica.shizuku.playground.tile.Lock

data class LockObject(
    val content: Block = Block.LOCK
) {
    lateinit var lock: Lock
}
