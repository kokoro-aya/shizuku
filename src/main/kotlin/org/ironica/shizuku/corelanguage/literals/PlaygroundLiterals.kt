package org.ironica.shizuku.corelanguage.literals

import org.ironica.shizuku.corelanguage.Variability
import org.ironica.shizuku.playground.*
import org.ironica.shizuku.playground.characters.Player
import org.ironica.shizuku.playground.characters.Specialist
import org.ironica.shizuku.playground.items.*
import org.ironica.shizuku.playground.shop.WeaponItem

data class PlayerLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Player,
    var placed: Boolean = false,
    ): Literal
data class SpecialistLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Specialist,
    var placed: Boolean = false,
    ): Literal

data class GemLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Gem,
    var placed: Boolean = false,
): Literal
data class GoldLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Gold,
    var placed: Boolean = false,
): Literal
data class SwitchLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Switch,
    var placed: Boolean = false,
): Literal
data class PlatformLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Platform,
    val controlledBy: Lock,
    var placed: Boolean = false,
): Literal
data class PortalLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: PortalObject,
    var placed: Boolean = false,
): Literal
data class PortionLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Portion,
    var placed: Boolean = false,
): Literal

data class TileLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Tile,
    var placed: Boolean = false,
): Literal
data class LavaLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Lava,
    var placed: Boolean = false,
): Literal
data class ShelterLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Shelter,
    var placed: Boolean = false,
): Literal
data class VillageLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Village,
    var placed: Boolean = false,
): Literal
data class StairLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Stair,
    var placed: Boolean = false,
): Literal
data class LockLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Lock,
    var placed: Boolean = false,
): Literal
data class MonsterLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: Monster,
    var placed: Boolean = false,
): Literal

data class WeaponLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    var content: WeaponItem,
    var placed: Boolean = false,
): Literal

data class PortionItemLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    val size: Size,
): Literal

data class WeaponItemLiteral(
    override val variability: Variability,
    override val prototype: Proto?,
    val id: Int,
): Literal