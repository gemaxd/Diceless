package com.example.diceless.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScryfallCard(
    val name: String,
    @Json(name = "image_uris") val imageUris: ImageUris?
)

@JsonClass(generateAdapter = true)
data class ImageUris(
    val small: String?,
    val normal: String?,
    val large: String?,
    val png: String?,
    @Json(name = "art_crop") val artCrop: String?,
    @Json(name = "border_crop") val borderCrop: String?
)