package org.ironica.shizuku.utils

import org.ironica.shizuku.bridge.*
import org.ironica.shizuku.bridge.initrules.rules.MonsterRule
import org.ironica.shizuku.bridge.initrules.rules.PortalOrLockRule
import org.ironica.shizuku.bridge.initrules.rules.Rules
import org.ironica.shizuku.playground.*

fun convertSingleBlockToTile(block: Block, i: Int, j: Int, tileInfo: TileInfo?, lockRules: PortalOrLockRule?, monsterRules: MonsterRule?): Tile = when (block) {
    Block.NONE -> None
    Block.OPEN -> Open
    Block.HILL -> Hill
    Block.STONE -> Stone
    Block.TREE -> Tree
    Block.WATER -> Water
    Block.LAVA -> Lava(0)
    Block.RUIN -> Ruin
    Block.SHELTER, Block.VILLAGE, Block.STAIR,
    Block.LOCK, Block.MONSTER ->
        if
            (tileInfo != null && lockRules != null && monsterRules != null) setASpecialTile(j, i, tileInfo, lockRules, monsterRules)
        else
            throw Exception()
}

private fun setASpecialTile(x: Int, y: Int, tiles: TileInfo, lockRules: PortalOrLockRule, monsterRules: MonsterRule): Tile {
    val coo = Coordinate(x, y)
    return with (tiles.locks.firstOrNull { it.coo == coo }
        ?: tiles.monsters.firstOrNull { it.coo == coo }
        ?: tiles.shelters.firstOrNull { it.coo == coo }
        ?: tiles.stairs.firstOrNull { it.coo == coo }
        ?: tiles.villages.firstOrNull { it.coo == coo } ?: throw Exception()) {
        setTileSub(this, lockRules, monsterRules)
    }
}

private fun setTileSub(tile: Any, lockRules: PortalOrLockRule, monsterRules: MonsterRule): Tile {
    return when (tile) {
        is ShelterInfo -> Shelter(tile.cap)
        is VillageInfo -> Village(tile.size)
        is StairInfo -> Stair(tile.dir)
        is LockInfo -> Lock(tile.controlled.toMutableList(), Color.WHITE, lockRules.defaultEnergy)
        is MonsterInfo -> Monster(tile.stamina, tile.atk, tile.level,
            monsterRules.defeatBonus.stamina[tile.level],
            monsterRules.defeatBonus.gem[tile.level],
            monsterRules.defeatBonus.gold[tile.level]
        )
        else -> throw Exception()
    }
}