package com.mabnets.jslradio.Views

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.ColorFilter
import android.os.IBinder
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mabnets.jslradio.R
import com.mabnets.jslradio.Viewmodels.Mediaplayerviewmodel

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import com.beraldo.playerlib.PlayerService

@Composable()
fun MainScreen(navController: NavController) {
    val musicviewmodel: Mediaplayerviewmodel = viewModel()
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .scrollable(scrollState, Orientation.Vertical)
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        musicviewmodel.getsecondcolor(),
                        musicviewmodel.getfirstcolor(),
                    )
                )
            )
            .padding(horizontal = 10.dp)
    ) {
        TopAppBar(navController)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Image Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sizeIn(maxWidth = 500.dp, maxHeight = 500.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .weight(10f)
            )
            Spacer(modifier = Modifier.height(30.dp))
            SongDescription("JESUS IS LORD RADIO", "streaming live")
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(10f)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                PlayerButtons(modifier = Modifier.padding(vertical = 8.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Otherbtns(navController)
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}


@Composable
fun TopAppBar(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Icon",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add list",
                tint = Color.White
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Icon",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SongDescription(
    title: String,
    name: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = name,
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            color = Color.White
        )
    }
}


@Composable
fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 42.dp
) {
    val audioFlag = remember { mutableStateOf(true) }
    val urls = "https://s3.radio.co/s97f38db97/listen"
    val contexts = LocalContext.current
    val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {}

        /*
     * Called after a successful bind with our PlayerService.
     */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            if (service is PlayerService.PlayerServiceBinder) {
                //service.getPlayerHolderInstance() // use the player and call methods on it to start and stop
                if (audioFlag.value) {
                    Toast.makeText(contexts, "Playing..", Toast.LENGTH_LONG).show()
                    service.getPlayerHolderInstance().start()
                } else {
                    service.getPlayerHolderInstance().stop()
                    Toast.makeText(contexts, "Pausing..", Toast.LENGTH_LONG).show()
                }

            } else {
                audioFlag.value = true
            }
        }
    }
    val intent = Intent(contexts, PlayerService::class.java).apply {
        putExtra(PlayerService.STREAM_URL, urls)
    }
    (contexts as Activity).applicationContext!!.bindService(
        intent,
        connection,
        Context.BIND_AUTO_CREATE
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonModifier = modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }
        val midlebtnModifier = modifier
            .size(playerButtonSize)
            .semantics { role = Role.Button }
            .clickable {
                if (audioFlag.value) {
                    audioFlag.value = false
                } else {
                    audioFlag.value = true
                }
            }

        Icon(
            painter = painterResource(R.drawable.ic_baseline_skip_next_24),
            contentDescription = "Skip Icon",
            modifier = buttonModifier,
            tint = Color.White
        )


        Icon(
            painter = if (audioFlag.value) {
                painterResource(R.drawable.ic_baseline_pause_24)
            } else {
                painterResource(R.drawable.ic_baseline_play_arrow_24)
            },
            contentDescription = "Play / Pause Icon",
            tint = Color.White,
            modifier = midlebtnModifier,
        )


        Icon(
            painter = painterResource(R.drawable.ic_baseline_skip_previous_24),
            contentDescription = "Replay 10 min",
            modifier = buttonModifier,
            tint = Color.White
        )


    }

}

@Composable
fun Otherbtns(navController: NavController) {
    val ggle="www.google.com"
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = { navController.navigate("webviews/$ggle") },
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            ),
            modifier = Modifier.fillMaxWidth(0.7f),
        ) {
            Text(
                text = "Other radio links",
                modifier = Modifier.padding(6.dp)
            )

        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
        ) {
            Text(
                text = "24/7 Endtime-Messages",
                color= Color.White,
                modifier = Modifier.padding(6.dp)

            )

        }
    }
}