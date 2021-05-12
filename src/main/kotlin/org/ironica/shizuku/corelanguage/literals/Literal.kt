package org.ironica.shizuku.corelanguage.literals

import org.ironica.shizuku.corelanguage.Variability

sealed interface Literal: Proto {
    val variability: Variability
}