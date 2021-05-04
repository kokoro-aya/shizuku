package org.ironica.shizuku.runner.initrules.preinitialized

import kotlinx.serialization.Serializable

@Serializable
data class PreInitializedObject(val name: String, val type: String, val param: Map<String, String>)