package com.irv205.mytestproject.data.service

import com.irv205.mytestproject.data.model.PokemonResponse
import retrofit2.http.GET

interface PokeService {

    @GET("pokemon")
    suspend fun getPokemonList(): PokemonResponse

}