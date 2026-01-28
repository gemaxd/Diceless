package com.example.diceless.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ScryfallCard(
    val name: String,

    @SerializedName("id")
    val cardId: String,

    @SerializedName("image_uris")
    val imageUris: ImageUris,

    @SerializedName("card_faces")
    val cardFaces: List<CardFace>?,

    @SerializedName("artist")
    val artistName: String,

    @SerializedName("prints_search_uri")
    val printsSearchUri: String = ""
)

@Serializable
data class CardFace(
    val name: String,
    @SerializedName("type_line") val typeLine: String?,

    @SerializedName("image_uris")
    val imageUris: ImageUris?
)

@Serializable
data class ImageUris(
    val small: String?,
    val normal: String?,
    val large: String?,
    val png: String?,

    @SerializedName("art_crop")
    val artCrop: String = "",

    @SerializedName("border_crop")
    val borderCrop: String?
)

fun ScryfallCard.toBackgroundProfile() : BackgroundProfileData =
    BackgroundProfileData(
        cardName = this.name,
        imageUri = this.imageUris.artCrop
    )
