package com.example.diceless.data.datasource.remote.dto

import com.example.diceless.domain.model.ScryfallCard
import com.google.gson.annotations.SerializedName

data class ScryfallSearchResponse(
    val data: List<ScryfallCard>,
    @SerializedName("total_cards") val totalCards: Int,
    @SerializedName("has_more") val hasMore: Boolean
)