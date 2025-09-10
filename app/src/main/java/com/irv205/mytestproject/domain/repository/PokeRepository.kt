package com.irv205.mytestproject.domain.repository

import com.irv205.mytestproject.core.util.ResponseHandler
import com.irv205.mytestproject.domain.model.PokemonList

interface PokeRepository {
    suspend fun getPokemonList(): ResponseHandler<PokemonList>
}