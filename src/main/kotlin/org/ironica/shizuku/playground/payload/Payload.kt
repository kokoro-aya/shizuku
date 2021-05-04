package org.ironica.shizuku.playground.payload

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.message.GameStatus

@Serializable
data class Payload(val playground: Array<PlaygroundPayload>, val status: GameStatus)