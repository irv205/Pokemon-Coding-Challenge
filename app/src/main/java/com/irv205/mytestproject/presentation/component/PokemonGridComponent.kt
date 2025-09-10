package com.irv205.mytestproject.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.irv205.mytestproject.domain.model.Pokemon
import com.irv205.mytestproject.presentation.MainViewModel

@Composable
fun PokemonGridScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    columns: Int = 2
) {
    val items = viewModel.pokemonList
    PokemonGrid(
        pokemon = items,
        modifier = modifier.fillMaxSize(),
        columns = columns
    )
}

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

@Composable
private fun PokemonCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val bg = backgroundForName(pokemon.name)
    val textColor = if (bg.luminance() < 0.5f) Color.White else Color.Black

    Card(
        modifier = modifier
            .aspectRatio(0.9f) // alto similar al ancho
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = bg),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = pokemon.url.asPokemonImageUrl(),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 4.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = pokemon.name.replaceFirstChar { it.titlecase() },
                style = MaterialTheme.typography.titleMedium,
                color = textColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/* ---------- Helpers ---------- */

// URL del "official artwork" usando el id de la URL de PokeAPI
fun String.asPokemonImageUrl(): String {
    val id = Regex("""/pokemon/(\d+)/?""").find(this)?.groupValues?.getOrNull(1)
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

// Paleta cíclica tipo “pastel”. Selección estable por nombre.
@Stable
private fun backgroundForName(name: String): Color {
    val palette = listOf(
        Color(0xFF2C7A7B), // teal
        Color(0xFFDD6B20), // orange
        Color(0xFF805AD5), // purple
        Color(0xFF2B6CB0), // blue
        Color(0xFF319795), // cyan
        Color(0xFF38A169), // green
        Color(0xFFB83280), // pink
        Color(0xFFDD6B20), // orange (again for variedad)
        Color(0xFF718096)  // gray
    )
    val idx = kotlin.math.abs(name.lowercase().hashCode()) % palette.size
    return palette[idx]
}

/* ---------- Preview ---------- */

@Preview(showBackground = true)
@Composable
private fun PokemonGridPreview() {
    val sample = listOf(
        Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
        Pokemon("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"),
        Pokemon("venusaur", "https://pokeapi.co/api/v2/pokemon/3/"),
        Pokemon("charmander", "https://pokeapi.co/api/v2/pokemon/4/"),
        Pokemon("charmeleon", "https://pokeapi.co/api/v2/pokemon/5/"),
        Pokemon("charizard", "https://pokeapi.co/api/v2/pokemon/6/"),
        Pokemon("squirtle", "https://pokeapi.co/api/v2/pokemon/7/"),
        Pokemon("wartortle", "https://pokeapi.co/api/v2/pokemon/8/")
    )
    PokemonGrid(pokemon = sample, modifier = Modifier.fillMaxSize(), columns = 2)
}