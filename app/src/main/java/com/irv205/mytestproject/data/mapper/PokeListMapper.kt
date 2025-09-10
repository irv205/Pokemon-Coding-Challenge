package com.irv205.mytestproject.data.mapper

import com.irv205.mytestproject.data.model.PokemonResponse
import com.irv205.mytestproject.domain.model.Pokemon
import com.irv205.mytestproject.domain.model.PokemonList

fun PokemonResponse.toDomain(): PokemonList =
    PokemonList(
        list = pokemon.map { dto ->
            Pokemon(
                name = dto.name,
                url = dto.url
            )
        }
    )