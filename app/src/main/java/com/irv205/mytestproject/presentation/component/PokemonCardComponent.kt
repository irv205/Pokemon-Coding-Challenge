package com.irv205.mytestproject.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.irv205.mytestproject.core.util.getPokemonImageUrl
import com.irv205.mytestproject.core.util.backgroundForName
import com.irv205.mytestproject.domain.model.Pokemon

@Composable
fun PokemonCard(
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
                model = pokemon.img,
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

@Preview(showBackground = true)
@Composable
fun PokemonCardPreview() {
    val samplePokemon = Pokemon(
        id = "",
        name = "Pikachu",
        img = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
    )

    MaterialTheme {
        PokemonCard(
            pokemon = samplePokemon,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {}
        )
    }
}