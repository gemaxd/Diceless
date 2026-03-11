package com.manarimjesse.diceless.core.extensions

import com.manarimjesse.diceless.domain.HistoryPlayerBasicData
import com.manarimjesse.diceless.domain.model.PlayerData

fun List<PlayerData>.toHistoryPlayerBasicDataList() : List<HistoryPlayerBasicData> {
    return this.map { playerData ->  playerData.toHistoryPlayerBasicData()}
}

fun PlayerData.toHistoryPlayerBasicData() : HistoryPlayerBasicData {
    return HistoryPlayerBasicData(
        name = name,
        backgroundImageUri = backgroundProfile?.imageUri ?: "",
        playerPosition = playerPosition
    )
}