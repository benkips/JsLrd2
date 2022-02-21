package com.mabnets.jslradio.Views


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mabnets.jslradio.R
import kotlinx.coroutines.delay
@Composable()
fun Launcher(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.background(Color.White),
                contentAlignment = Alignment.Center
            ) {
            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(5.dp)),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "JESUS IS LORD RADIO"
            )
            }
            Text(text = "JESUS IS lORD RADIO app",
            )
        }

    }
    LaunchedEffect(key1 =true){
        delay(1000)
            navController.navigate("Mainscreen"){
                popUpTo("Launcher"){
                    inclusive=true
                }

        }
    }
}