package com.irv205.mytestproject.data.repository

import com.irv205.mytestproject.core.util.ResponseHandler
import com.irv205.mytestproject.data.mapper.toDomain
import com.irv205.mytestproject.data.service.PokeService
import com.irv205.mytestproject.domain.model.PokemonList
import com.irv205.mytestproject.domain.repository.PokeRepository
import javax.inject.Inject

class PokeRepositoryImpl @Inject constructor(
    private val service: PokeService
): PokeRepository {
    private var nextUrl: String? = null

    override suspend fun getPokemonList(): ResponseHandler<PokemonList> {
        return try {
            val response = service.getPokemonList()
            nextUrl = response.next
            ResponseHandler.Success(response.toDomain())
        } catch (e: Exception) {
            ResponseHandler.Error(e.message.toString())
        }
    }

    override suspend fun loadNextPokemonPage(): ResponseHandler<PokemonList> {
        return try {
                val response = service.nextPokemonPage(nextUrl ?: "")
                nextUrl = response.next
                ResponseHandler.Success(response.toDomain())
        }  catch (e: Exception) {
            ResponseHandler.Error(e.message.toString())
        }
    }
}