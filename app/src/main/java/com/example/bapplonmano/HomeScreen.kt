// HomeScreen.kt
package com.example.bapplonmano

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Bapplonmano",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        IconButtons(navController)

        PowerButton()
    }
}

@Composable
fun IconButtons(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(navController, R.drawable.ic_equipo, "Equipo")
        IconButton(navController, R.drawable.ic_competicion, "Competición")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(navController, R.drawable.ic_partido, "Partido")
        IconButton(navController, R.drawable.ic_estadisticas, "Estadísticas")
    }
}

@Composable
fun IconButton(navController: NavHostController, iconRes: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            if (label == "Equipo") {
                navController.navigate("team")
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .background(Color.Red, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        Text(text = label, color = Color.White)
    }
}


@Composable
fun PowerButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(80.dp)
            .background(Color.White, shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_power),
            contentDescription = "Power",
            tint = Color.Red,
            modifier = Modifier.size(40.dp)
        )
    }
}
