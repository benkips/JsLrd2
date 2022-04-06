package com.mabnets.jslradio

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import org.jetbrains.anko.AnkoLogger
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.mabnets.jslradio.Viewmodels.Launcherviewmodel
import com.mabnets.jslradio.Viewmodels.Mediaplayerviewmodel
import com.mabnets.jslradio.Views.Launcher
import com.mabnets.jslradio.Views.MainScreen
import com.mabnets.jslradio.Views.Webviewscreen
import com.mabnets.jslradio.ui.theme.JesusisLORDTheme
import com.mabnets.jslradio.util.showPermissionRequestExplanation

class MainActivity : ComponentActivity(),AnkoLogger {
    private val launcherviewmodel :Launcherviewmodel by viewModels()
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    val TOPIC="Alertstwo"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                launcherviewmodel.isloading.value
            }

        }
        //FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (!granted) {
                    Toast.makeText(this, "Storage Permission NOT Granted", Toast.LENGTH_SHORT).show()
                    requestStoragePermission()
                }
            }
        requestStoragePermission()
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
    //asking for permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestStoragePermission(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // The permission is granted
                // you can go with the flow that requires permission here
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                // This case means user previously denied the permission
                // So here we can display an explanation to the user
                // That why exactly we need this permission
                showPermissionRequestExplanation(
                    getString(R.string.write_storage),
                    getString(R.string.permission_request)
                ) { requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) }
            }
            else -> {
                // Everything is fine you can simply request the permission
                requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}

