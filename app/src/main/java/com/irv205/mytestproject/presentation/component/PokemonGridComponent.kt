package com.irv205.mytestproject.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irv205.mytestproject.domain.model.Pokemon

@Composable
fun PokemonGrid(
    pokemon: List<Pokemon>,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    onItemClick: (Pokemon) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pokemon, key = { it.name }) { p ->
            PokemonCard(
                pokemon = p,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onItemClick(p) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonGridPreview() {
    val samplePokemonList = listOf(
        Pokemon("","Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"),
        Pokemon("","Charmander", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"),
        Pokemon("","Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
        Pokemon("","Squirtle", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png")
    )

    MaterialTheme {
        PokemonGrid(
            pokemon = samplePokemonList,
            modifier = Modifier.fillMaxSize(),
            columns = 2,
            onItemClick = {}
        )
    }
}