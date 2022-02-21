package com.mabnets.jslradio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mabnets.jslradio.Views.Launcher
import com.mabnets.jslradio.Views.MainScreen
import com.mabnets.jslradio.ui.theme.JesusisLORDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JesusisLORDTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    navigationcontroller()
                }
            }
        }
    }
@Composable
    private fun navigationcontroller() {
    val navcontroler = rememberNavController()
    NavHost(navController = navcontroler,
        startDestination = "Launcher",
        builder = {
            composable("Launcher", content = { Launcher(navController = navcontroler) })
            composable("Mainscreen", content = { MainScreen(navController = navcontroler) })
        }
    )
    }
}

