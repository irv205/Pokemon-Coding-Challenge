package com.irv205.mytestproject.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irv205.mytestproject.core.util.ResponseHandler
import com.irv205.mytestproject.domain.model.Pokemon
import com.irv205.mytestproject.domain.model.PokemonList
import com.irv205.mytestproject.domain.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    var pokemonList by mutableStateOf<List<Pokemon>>(emptyList())


    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = repository.getPokemonList()) {
                is ResponseHandler.Error -> {
                    Log.e("ERROR====", result.message)
                }
                is ResponseHandler.Success -> {
                    pokemonList = result.data.list
                    Log.e("Success=====", result.data.toString())
                }
            }
        }
    }
}