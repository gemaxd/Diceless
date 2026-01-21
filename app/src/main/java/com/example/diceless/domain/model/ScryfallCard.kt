package com.example.diceless.domain.model

import com.google.gson.annotations.SerializedName

data class ScryfallCard(
    val name: String,

    @SerializedName("image_uris")
    val imageUris: ImageUris?,

    @SerializedName("card_faces")
    val cardFaces: List<CardFace>?
)

data class CardFace(
    val name: String,
    @SerializedName("type_line") val typeLine: String?,

    @SerializedName("image_uris")
    val imageUris: ImageUris?
)

data class ImageUris(
    val small: String?,
    val normal: String?,
    val large: String?,
    val png: String?,

    @SerializedName("art_crop")
    val artCrop: String?,

    @SerializedName("border_crop")
    val borderCrop: String?
)

fun ScryfallCard.toBackgroundProfile() : BackgroundProfileData =
    BackgroundProfileData(
        cardName = this.name,
        imageUri = this.imageUris?.artCrop
    )
