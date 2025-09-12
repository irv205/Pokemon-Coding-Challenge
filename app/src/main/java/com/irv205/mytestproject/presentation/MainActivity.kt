package com.irv205.mytestproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.irv205.mytestproject.presentation.home.PokemonScreen
import com.irv205.mytestproject.presentation.ui.theme.MyTestProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTestProjectTheme {
                PokemonScreen(
                    columns = 3
                )
            }
        }
    }
}

