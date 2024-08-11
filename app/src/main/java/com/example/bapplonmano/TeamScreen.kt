// TeamScreen.kt
package com.example.bapplonmano

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Player(val number: Int, val name: String, val isGoalkeeper: Boolean)

@Composable
fun TeamScreen(navController: NavHostController) {
    var teamName by remember { mutableStateOf("My Team") }
    var players by remember { mutableStateOf(List(19) { index ->
        Player(index + 1, "", index >= 16)
    }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        BasicTextField(
            value = teamName,
            onValueChange = { teamName = it },
            textStyle = TextStyle(color = Color.White, fontSize = 24.sp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        players.forEachIndexed { index, player ->
            PlayerRow(player) { updatedPlayer ->
                players = players.toMutableList().also {
                    it[index] = updatedPlayer
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Close")
        }
    }
}

@Composable
fun PlayerRow(player: Player, onPlayerChange: (Player) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (player.isGoalkeeper) "G" else player.number.toString(),
            color = Color.White,
            modifier = Modifier.width(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        BasicTextField(
            value = player.name,
            onValueChange = { onPlayerChange(player.copy(name = it)) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp)
        )
    }
}
