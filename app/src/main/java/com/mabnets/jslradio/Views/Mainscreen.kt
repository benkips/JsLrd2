package com.mabnets.jslradio.Views

import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mabnets.jslradio.R
import com.mabnets.jslradio.Viewmodels.Mediaplayerviewmodel

import androidx.compose.foundation.background
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

@Composable()
fun MainScreen(navController: NavController) {
    val musicviewmodel: Mediaplayerviewmodel = viewModel()
    Column(
        modifier = Modifier
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
            SongDescription("Audio Name", "State Diagram – Maintain Payment Plans")
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(10f)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                PlayerButtons(modifier = Modifier.padding(vertical = 8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}



@Composable
fun TopAppBar(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = {navController.navigate("Login_page")  }) {
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }
        val midlebtnModifier = Modifier
            .size(playerButtonSize)
            .semantics { role = Role.Button }

        Icon(
            painter = painterResource(R.drawable.ic_baseline_skip_next_24),
            contentDescription = "Skip Icon",
            modifier = buttonModifier,
            tint = Color.White
        )

        Icon(
            painter = painterResource(R.drawable.ic_baseline_play_arrow_24),
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