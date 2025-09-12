package com.irv205.mytestproject.data.service

import com.irv205.mytestproject.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonResponse

    @GET
    suspend fun nextPokemonPage(@Url nextUrl: String): PokemonResponse

}