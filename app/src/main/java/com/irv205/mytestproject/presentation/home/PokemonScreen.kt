package com.irv205.mytestproject.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.irv205.mytestproject.domain.model.Pokemon
import com.irv205.mytestproject.presentation.MainViewModel
import com.irv205.mytestproject.presentation.component.PokemonGrid

@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    columns: Int = 2
) {
    val state = viewModel.uiState

    LaunchedEffect(state.items.size) {
        if (state.items.isNotEmpty() && !state.loading && !state.endReached) {
            viewModel.loadMore()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PokemonGrid(
            pokemon = state.items,
            modifier = modifier.fillMaxSize(),
            columns = columns
        )

        if (state.error != null) {
            if (state.items.isEmpty()) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = state.error)
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun PokemonGridPreview() {
    val sample = listOf(
        Pokemon("","bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
        Pokemon("","ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"),
        Pokemon("","venusaur", "https://pokeapi.co/api/v2/pokemon/3/"),
        Pokemon("","charmander", "https://pokeapi.co/api/v2/pokemon/4/"),
        Pokemon("","charmeleon", "https://pokeapi.co/api/v2/pokemon/5/"),
        Pokemon("","charizard", "https://pokeapi.co/api/v2/pokemon/6/"),
        Pokemon("","squirtle", "https://pokeapi.co/api/v2/pokemon/7/"),
        Pokemon("","wartortle", "https://pokeapi.co/api/v2/pokemon/8/")
    )
    PokemonGrid(pokemon = sample, modifier = Modifier.fillMaxSize(), columns = 3)
}