package org.ironica.shizuku.bridge

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.Size

@Serializable
data class ShopInfo(
    val weapons: List<WeaponShopInfo>,
    val portions: List<PortionShopInfo>,
)


@Serializable
data class WeaponShopInfo(
    val id: Int,
    val atk: Int,
    val cost: Int,
)

@Serializable
data class PortionShopInfo(
    val size: Size,
    val remains: Int,
    val cost: Int,
)