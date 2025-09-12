package com.irv205.mytestproject.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.irv205.mytestproject.R

@Composable
fun PokeLoaderComponent(
    modifier: Modifier = Modifier,
    rawRes: Int? = R.raw.pokeball,
    urlFallback: String = "https://lottie.host/e0366996-63bd-47db-bea6-e3b0f791a7d6/DUeljdQ9sK.lottie"
) {
    val composition by rememberLottieComposition(
        rawRes?.let { LottieCompositionSpec.RawRes(it) }
            ?: LottieCompositionSpec.Url(urlFallback)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}