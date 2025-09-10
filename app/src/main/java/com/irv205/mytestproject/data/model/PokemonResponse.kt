package com.irv205.mytestproject.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("results")
    val pokemon: List<PokemonDTO>
)

data class PokemonDTO(
    val name: String,
    val url: String
)