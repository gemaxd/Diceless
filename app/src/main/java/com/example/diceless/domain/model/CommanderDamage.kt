package com.example.diceless.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CommanderDamage(
    val name: String,
    var damage: Int = 0
)