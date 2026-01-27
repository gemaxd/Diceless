package com.example.diceless.data.repository

import com.example.diceless.data.ScryfallApi
import com.example.diceless.domain.model.ScryfallCard
import retrofit2.HttpException
import java.net.URLEncoder
import javax.inject.Inject

interface ScryFallRepository {
    suspend fun searchCards(query: String): Result<List<ScryfallCard>>
    suspend fun searchForAllPrints(printsSearchUri: String): Result<List<ScryfallCard>>
}

class ScryFallRepositoryImpl @Inject constructor(
    private val api: ScryfallApi
): ScryFallRepository {
    private val cache = mutableMapOf<String, List<ScryfallCard>>()

    override suspend fun searchCards(query: String): Result<List<ScryfallCard>> {
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

        } catch (e: HttpException) {
            if (e.code() == 400 || e.code() == 404) {
                Result.failure(Exception("Nenhum resultado para '$q'"))
            } else {
                Result.failure(e)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchForAllPrints(printsSearchUri: String): Result<List<ScryfallCard>> {
        return try {
            val response = api.getPrints(printsSearchUri)

            if (response.data.isEmpty()) {
                return Result.failure(Exception("Nenhum card encontrado"))
            }

            Result.success(response.data)

        } catch (e: HttpException) {
            if (e.code() == 400 || e.code() == 404) {
                Result.failure(Exception("Nenhum resultado"))
            } else {
                Result.failure(e)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}