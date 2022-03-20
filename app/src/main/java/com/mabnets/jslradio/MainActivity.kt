package com.mabnets.jslradio

import android.os.Bundle
import android.widget.Toast
import org.jetbrains.anko.AnkoLogger
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mabnets.jslradio.Viewmodels.Launcherviewmodel
import com.mabnets.jslradio.Viewmodels.Mediaplayerviewmodel
import com.mabnets.jslradio.Views.Launcher
import com.mabnets.jslradio.Views.MainScreen
import com.mabnets.jslradio.Views.Webviewscreen
import com.mabnets.jslradio.ui.theme.JesusisLORDTheme

class MainActivity : ComponentActivity(),AnkoLogger {
    private val launcherviewmodel :Launcherviewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                launcherviewmodel.isloading.value
            }

        }
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
        startDestination = "Mainscreen",
        builder = {
           // composable("Launcher", content = { Launcher(navController = navcontroler) })
            composable("Mainscreen", content = { MainScreen(navController = navcontroler) })
            composable(
                "webviews/{urls}",
                arguments = listOf(
                    navArgument("urls") {
                        type = NavType.StringType
                    }
                ),
            ){
                navBackStackEntry ->
                navBackStackEntry.arguments?.getString("urls")?.let { urlz ->
                    Webviewscreen(urlz)
                }
            }
        }
    )
    }
}

