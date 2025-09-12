package com.irv205.mytestproject.core.util

fun String.getPokemonId(): String {
    return Regex("/pokemon/(\\d+)/?")
        .find(this)
        ?.groupValues
        ?.getOrNull(1)
        ?: "0"
}