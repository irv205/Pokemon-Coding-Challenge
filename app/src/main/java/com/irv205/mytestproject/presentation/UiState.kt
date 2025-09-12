package com.irv205.mytestproject.presentation

import com.irv205.mytestproject.domain.model.Pokemon

data class UiState(
    val items: List<Pokemon> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false
)