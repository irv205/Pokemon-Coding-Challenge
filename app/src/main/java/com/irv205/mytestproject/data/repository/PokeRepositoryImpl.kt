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

    override suspend fun getPokemonList(): ResponseHandler<PokemonList> {
        return try {
            ResponseHandler.Success(service.getPokemonList().toDomain())
        } catch (e: Exception) {
            ResponseHandler.Error(e.message.toString())
        }
    }
}