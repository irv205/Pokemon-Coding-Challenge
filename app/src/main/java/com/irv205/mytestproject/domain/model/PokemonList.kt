package com.irv205.mytestproject.domain.model

data class PokemonList(
    val list: List<Pokemon>
)

data class Pokemon(
    val id: String,
    val name: String,
    val img: String
)