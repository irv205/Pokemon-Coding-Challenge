package com.irv205.mytestproject.core.util

// URL del "official artwork" usando el id de la URL de PokeAPI
fun String.getPokemonImageUrl(): String {
    val id = Regex("""/pokemon/(\d+)/?""").find(this)?.groupValues?.getOrNull(1)
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}