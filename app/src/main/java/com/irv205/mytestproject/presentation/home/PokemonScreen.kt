package com.irv205.mytestproject.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.irv205.mytestproject.domain.model.Pokemon
import com.irv205.mytestproject.presentation.MainViewModel
import com.irv205.mytestproject.presentation.UiState
import com.irv205.mytestproject.presentation.component.PokeLoaderComponent
import com.irv205.mytestproject.presentation.component.PokemonGrid

@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    // Cargar más SOLO cuando el usuario llega al final visible
    val shouldLoadMore by remember(state, gridState) {
        derivedStateOf {
            val s = state
            val last = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val total = gridState.layoutInfo.totalItemsCount
            val nearEnd = total > 0 && last >= total - 6
            s is UiState.Success &&
                    !s.endReached && !s.appending && s.appendError == null &&
                    nearEnd
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) viewModel.loadMore()
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val s = state) {
            UiState.Loading -> {
                PokeLoaderComponent(
                    modifier = Modifier.align(Alignment.Center).size(128.dp)
                )
            }

            is UiState.Success -> {
                PokemonGrid(
                    pokemon = s.items,
                    modifier = Modifier.fillMaxSize(),
                    columns = columns,
                    gridState = gridState
                )

                if (s.appending) {
                    PokeLoaderComponent(
                        modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp).size(72.dp)
                    )
                }
                s.appendError?.let { msg ->
                    Column(
                        Modifier.align(Alignment.BottomCenter).padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(msg)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadMore() }) {
                            Text("Reintentar cargar más")
                        }
                    }
                }
            }

            is UiState.Error -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(s.message)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.refresh() }) { Text("Reintentar") }
                }
            }

            UiState.Empty -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No hay Pokémon disponibles")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.refresh() }) { Text("Actualizar") }
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