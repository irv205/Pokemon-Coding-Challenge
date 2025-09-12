package com.irv205.mytestproject.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irv205.mytestproject.core.util.ResponseHandler
import com.irv205.mytestproject.domain.model.Pokemon
import com.irv205.mytestproject.domain.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val loadMutex = Mutex()

    init {
        refresh()
    }


    fun refresh() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val res = repository.getPokemonList()) {
                is ResponseHandler.Success -> {
                    val list = res.data.list.orEmpty() // nunca nulo
                    _uiState.update {
                        if (list.isEmpty()) UiState.Empty
                        else UiState.Success(items = list, endReached = false)
                    }
                }
                is ResponseHandler.Error -> {
                    _uiState.value = UiState.Error(res.message)
                }
            }
        }
    }


    fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            loadMutex.withLock {
                val current = _uiState.value
                if (current !is UiState.Success || current.endReached || current.appending) return@withLock

                _uiState.update { (it as UiState.Success).copy(appending = true, appendError = null) }

                try {
                    when (val res = repository.loadNextPokemonPage()) {
                        is ResponseHandler.Success -> {
                            val next = res.data.list.orEmpty()
                            val merged = (current.items + next).distinctBy { it.name }
                            _uiState.update {
                                UiState.Success(
                                    items = merged,
                                    endReached = next.isEmpty(),
                                    appending = false,
                                    appendError = null
                                )
                            }
                        }
                        is ResponseHandler.Error -> {
                            _uiState.update {
                                (it as UiState.Success).copy(appending = false, appendError = res.message)
                            }
                        }
                    }
                } catch (t: Throwable) {
                    _uiState.update {
                        (it as UiState.Success).copy(appending = false, appendError = t.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}