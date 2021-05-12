package org.ironica.shizuku.playground.message

import kotlinx.serialization.Serializable
import org.ironica.shizuku.playground.payload.Payload

@Serializable
sealed class Message

@Serializable
data class NormalMessage(val status: Status, val payload: Payload): Message()

@Serializable
data class ErrorMessage(val status: Status, val msg: String): Message()