package com.example.diceless.data

import com.example.diceless.domain.model.ScryfallCard
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

object CardRepository {
    private val api: ScryfallApi = Retrofit.Builder()
        .baseUrl("https://api.scryfall.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ScryfallApi::class.java)

    private val cache = mutableMapOf<String, List<ScryfallCard>>()

    suspend fun buscarCards(query: String): Result<List<ScryfallCard>> {
        val q = query.trim()
        if (q.isBlank()) return Result.failure(IllegalArgumentException("Digite algo"))

        cache[q]?.let { return Result.success(it) }

        return try {
            val encoded = URLEncoder.encode(q, "UTF-8")
            val response = api.searchCards(encoded)

            if (response.data.isEmpty()) {
                return Result.failure(Exception("Nenhum card encontrado"))
            }

            cache[q] = response.data
            Result.success(response.data)

        } catch (e: retrofit2.HttpException) {
            if (e.code() == 400 || e.code() == 404) {
                Result.failure(Exception("Nenhum resultado para '$q'"))
            } else {
                Result.failure(e)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}