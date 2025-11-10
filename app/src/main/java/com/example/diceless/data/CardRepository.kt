package com.example.diceless.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CardRepository {
    private val api: ScryfallApi = Retrofit.Builder()
        .baseUrl("https://api.scryfall.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ScryfallApi::class.java)

    // Cache em memória (cardName -> url)
    private val cache = mutableMapOf<String, String>()

    suspend fun buscarImagem(nomeCard: String): Result<String> {
        val nomeNormalizado = nomeCard.trim()

        // 1. Cache rápido
        cache[nomeNormalizado]?.let { return Result.success(it) }

        return try {
            val card = api.getCardByExactName(nomeNormalizado)
            val url = card.imageUris?.normal
                ?: card.imageUris?.png
                ?: card.imageUris?.large
                ?: return Result.failure(Exception("Imagem não encontrada"))

            cache[nomeNormalizado] = url
            Result.success(url)
        } catch (e: Exception) {
            // Se der 404 ou erro, tenta busca fuzzy (opcional)
            Result.failure(e)
        }
    }
}