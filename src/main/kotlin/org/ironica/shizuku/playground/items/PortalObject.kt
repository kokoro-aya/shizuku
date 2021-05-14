package org.ironica.shizuku.playground.items

import org.ironica.shizuku.playground.Color
import org.ironica.shizuku.playground.playground.Playground

data class PortalObject(
    val color: Color,
    val playground: Playground,
    var isActive: Boolean,
    var portal: Portal?,
) {
    fun toggle(): Boolean {
        return if (playground.portalToggle(this.portal)) {
            isActive = !isActive
            true
        } else false
    }
}
