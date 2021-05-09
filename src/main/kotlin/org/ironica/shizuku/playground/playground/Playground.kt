package org.ironica.shizuku.playground.playground

import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.AbstractPlayer
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.tile.*
import org.ironica.shizuku.playground.world.World
import org.ironica.shizuku.runner.GemOrBeeper
import org.ironica.shizuku.runner.data.PlayerData
import org.ironica.shizuku.runner.initrules.playgroundrules.*
import org.ironica.shizuku.runner.initrules.specialrules.SpecialMessage

class Playground(
    val tiles: Tiles,
    val portals: MutableList<Portal>,
    val locks: List<Lock>,
    val players: MutableMap<Player, Coordinate> = mutableMapOf(),
    playerDatas: Array<PlayerData>,

    var worlds: MutableList<World> = mutableListOf(),

    val additionalGems: MutableList<GemOrBeeper>,
    val additionalBeepers: MutableList<GemOrBeeper>,

    randomInitGems: Int,
    randomInitBeepers: Int,

    randomInitPortals: Int,

    val userCollision: Boolean,
    val maxTerrainHeight: Int,
    val canStackOnTerrain: Boolean,
    val canPutBlockOnVoid: Boolean,

    val eraseTerrainCondition: EraseTerrainCondition,
    val winCondition: WinCondition,
    val loseCondition: LoseCondition,

    val staminaRule: StaminaRules,

    val swimRules: SwimRules,
    val lavaRules: LavaRules,

    val beeperDisappearAfter: Int,
    gemDisappearAfter: Int,
    val autoFailRule: AutoFailRule,

    additionalGemOneAfterAnother: Boolean,
    additionalBeeperOneAfterAnother: Boolean,

    val defaultPortalEnergy: Int,
    defaultLockEnergy: Int,

    val decreaseEachUsageOfPortal: Int,
    val decreaseEachUsageOfLock: Int,

    val seasonRules: SeasonRules,

    val specialMessages: List<SpecialMessage>,

    var currentTurns: Int = 0
) {
    init {
    }

    fun win(): Boolean {  }
    fun lose(): Boolean {  }
    fun pending(): Boolean {  }

    private fun prePrintTile(x: Int, y: Int): String {
    }

    fun printGrid() {
    }

    private fun getCooFor(player: Player): Coordinate {
    }

    private fun isTileAccessible(tile: Tile): Boolean {
    }

    private fun isBlockBlocked(from: Coordinate, to: Coordinate): Boolean {
    }

    private fun isAdjacent(from: Coordinate, to: Coordinate): Boolean {
    }

    private fun hasStairToward(from: Coordinate, to: Coordinate): Boolean {
    }

    private fun isBlockedYPlus(player: Player): Boolean {
    }
    private fun isBlockedYMinus(player: Player): Boolean {
    }
    private fun isBlockedXMinus(player: Player): Boolean {
    }
    private fun isBlockedXPlus(player: Player): Boolean {
    }

    fun playerTurnLeft(player: Player): Boolean {
    }
    fun playerTurnRight(player: Player): Boolean {
    }

    private fun movePlayerUp(player: Player) {
    }
    private fun movePlayerDown(player: Player) {
    }
    private fun movePlayerLeft(player: Player) {
    }
    private fun movePlayerRight(player: Player) {
    }

    private fun incrementATurn() {
    }

    private fun getAmplitudeForPlayer(player: Player): Double {
    }

    private fun gameIsWin(): Boolean {
    }
    private fun gameIsLost(): Boolean {
    }

    private fun afterPlayerMakesAMove(player: Player, oldCoo: Coordinate, newCoo: Coordinate) {
    }

    fun playerMoveForward(player: Player): Boolean {
    }
    fun playerCollectGem(player: Player): Boolean {
    }
    fun playerToggleSwitch(player: Player): Boolean {
    }
    fun playerTakeBeeper(player: Player): Boolean {
    }
    fun playerDropBeeper(player: Player): Boolean {
    }
    fun playerKill(player: Player): Boolean {
    }
    fun playerSetUpShelter(player: Player): Boolean {
    }
    fun playerIsOnGem(player: Player): Boolean {
    }
    fun playerIsOnOpenedSwitch(player: Player): Boolean {
    }
    fun playerIsOnClosedSwitch(player: Player): Boolean {
    }
    fun playerIsOnBeeper(player: Player): Boolean {
    }
    fun playerIsAtHome(player: Player): Boolean {
    }
    fun playerIsInDesert(player: Player): Boolean {
    }
    fun playerIsInForest(player: Player): Boolean {
    }
    fun playerIsInWater(player: Player): Boolean {
    }
    fun playerIsInLava(player: Player): Boolean {
    }
    fun playerIsInVoid(player: Player): Boolean {
    }
    fun playerIsOnPortal(player: Player): Boolean {
    }
    fun playerIsBlocked(player: Player): Boolean {
    }
    fun playerIsBlockedLeft(player: Player): Boolean {
    }
    fun playerIsBlockedRight(player: Player): Boolean {
    }
    fun playerIsInWinter(player: Player): Boolean {
    }
    fun playerIsInShelter(player: Player): Boolean {
    }

    fun playerChangeColor(player: Player, color: Color): Boolean {
    }
    fun playerJump(player: Player): Boolean {
    }

    fun playerStepIntoPortal(player: Player): Boolean {
    }

    fun specialistIsBeforeLock(specialist: Specialist): Boolean {
    }

    fun specialistTurnLockUp(specialist: Specialist): Boolean {
    }
    fun specialistTurnLockDown(specialist: Specialist): Boolean {
    }
    fun specialistExtendShelter(specialist: Specialist): Boolean {
    }

    fun worldPlace(player: AbstractPlayer, at: Coordinate): Boolean {
    }
    fun worldPlace(item: Item, at: Coordinate): Boolean {
    }
    fun worldPlace(platform: Platform, at: Coordinate): Boolean {
    }
    fun worldPlace(portal: Portal): Boolean {
    }
    fun worldPlace(stair: Stair): Boolean {
    }
    fun worldPlace(block: Block, at: Coordinate): Boolean {
    }

    private fun removeEverythingOnTile(x: Int, y: Int) {
    }

    fun levelDown(at: Coordinate): Boolean {
    }
    fun waitATurn(): Boolean {
    }
    fun setToWin(): Boolean {
    }
    fun setToLost(): Boolean {
    }

    fun getHeight(x: Int, y: Int): Int {
    }
}