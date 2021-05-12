package org.ironica.shizuku.corelanguage.literals

import org.ironica.shizuku.corelanguage.Variability
import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.items.*

data class PlayerLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Player,
    var initialized: Boolean = false,
    ): Literal
data class SpecialistLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Specialist,
    var initialized: Boolean = false,
    ): Literal

data class GemLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Gem,
    var initialized: Boolean = false,
): Literal
data class GoldLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Gold,
    var initialized: Boolean = false,
): Literal
data class SwitchLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Switch,
    var initialized: Boolean = false,
): Literal
data class PlatformLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Platform,
    val controlledBy: Lock,
    var initialized: Boolean = false,
): Literal
data class PortalLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Portal,
    var initialized: Boolean = false,
): Literal
data class PortionLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Portion,
    var initialized: Boolean = false,
): Literal

data class TileLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Tile,
    var initialized: Boolean = false,
): Literal
data class LavaLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Lava,
    var initialized: Boolean = false,
): Literal
data class ShelterLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Shelter,
    var initialized: Boolean = false,
): Literal
data class VillageLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Village,
    var initialized: Boolean = false,
): Literal
data class StairLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Stair,
    var initialized: Boolean = false,
): Literal
data class LockLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Lock,
    var initialized: Boolean = false,
): Literal
data class MonsterLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Monster,
    var initialized: Boolean = false,
): Literal
