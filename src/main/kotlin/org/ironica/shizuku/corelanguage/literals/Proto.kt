package org.ironica.shizuku.corelanguage.literals

sealed interface Proto {
    val prototype: Proto?
}