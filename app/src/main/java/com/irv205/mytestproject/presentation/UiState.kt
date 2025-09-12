package com.irv205.mytestproject.presentation

import com.irv205.mytestproject.domain.model.Pokemon

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Success(
        val items: List<Pokemon>,
        val endReached: Boolean,
        val appending: Boolean = false,
        val appendError: String? = null
    ) : UiState()
    data class Error(val message: String) : UiState()
}