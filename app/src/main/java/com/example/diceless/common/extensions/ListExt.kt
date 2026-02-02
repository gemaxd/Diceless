package com.example.diceless.common.extensions

import com.example.diceless.domain.HistoryPlayerBasicData
import com.example.diceless.domain.model.PlayerData

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