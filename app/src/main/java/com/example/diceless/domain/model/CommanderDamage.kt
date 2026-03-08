package com.example.diceless.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CommanderDamage(
    val backgroundProfile: BackgroundProfileData? = null,
    val name: String,
    var damage: Int = 0
)