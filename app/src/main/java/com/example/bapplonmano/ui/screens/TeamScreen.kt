package com.example.bapplonmano.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bapplonmano.Player
import com.example.bapplonmano.PlayerEntity
import com.example.bapplonmano.PlayerViewModel

@Composable
fun TeamScreen(navController: NavHostController) {
    val playerViewModel: PlayerViewModel = viewModel()
    val players by playerViewModel.players.collectAsState(initial = emptyList())

    var teamName by remember { mutableStateOf("My Team") }
    var selectedPlayerIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        BasicTextField(
            value = teamName,
            onValueChange = { teamName = it },
            textStyle = TextStyle(color = Color.White, fontSize = 24.sp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (players.isNotEmpty()) {
            PlayerGrid(players = players.map { Player(it.number, it.name, it.isGoalkeeper) }, onPlayerChange = { updatedPlayer, index ->
                val playerEntity = players[index].copy(
                    number = updatedPlayer.number,
                    name = updatedPlayer.name,
                    isGoalkeeper = updatedPlayer.isGoalkeeper
                )
                playerViewModel.updatePlayer(playerEntity)
            }, onPlayerClick = { index ->
                selectedPlayerIndex = index
            })
        } else {
            Text(text = "No players found", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Close")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            playerViewModel.deleteAllPlayers()
        }) {
            Text(text = "Reset Team")
        }
    }

    if (selectedPlayerIndex >= 0) {
        val player = players[selectedPlayerIndex]
        EditPlayerDialog(Player(player.number, player.name, player.isGoalkeeper)) { updatedPlayer ->
            val playerEntity = players[selectedPlayerIndex].copy(
                number = updatedPlayer.number,
                name = updatedPlayer.name,
                isGoalkeeper = updatedPlayer.isGoalkeeper
            )
            playerViewModel.updatePlayer(playerEntity)
            selectedPlayerIndex = -1
        }
    }
}

@Composable
fun PlayerGrid(players: List<Player>, onPlayerChange: (Player, Int) -> Unit, onPlayerClick: (Int) -> Unit) {
    Column {
        for (row in 0 until 5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (col in 0 until 4) {
                    val index = row * 4 + col
                    if (index < players.size) {
                        PlayerIcon(players[index], { updatedPlayer ->
                            onPlayerChange(updatedPlayer, index)
                        }) {
                            onPlayerClick(index)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PlayerIcon(player: Player, onPlayerChange: (Player) -> Unit, onPlayerClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onPlayerClick)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            Text(
                text = player.number.toString(),
                color = Color.Black,
                style = TextStyle(fontSize = 24.sp)
            )
        }
        Text(text = player.name.ifEmpty { "???" }, color = Color.Green)
    }
}

@Composable
fun EditPlayerDialog(player: Player, onPlayerChange: (Player) -> Unit) {
    var number by remember { mutableStateOf(player.number.toString()) }
    var name by remember { mutableStateOf(player.name) }
    val keyboardController = LocalSoftwareKeyboardController.current

    AlertDialog(
        onDismissRequest = { /* Dismiss dialog */ },
        confirmButton = {
            Button(
                onClick = {
                    val updatedPlayer = player.copy(number = number.toIntOrNull() ?: player.number, name = name)
                    onPlayerChange(updatedPlayer)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = { /* Dismiss dialog */ }) {
                Text("Cancel")
            }
        },
        title = { Text(text = "Edit Player") },
        text = {
            Column {
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    label = { Text("Number") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        val updatedPlayer = player.copy(number = number.toIntOrNull() ?: player.number, name = name)
                        onPlayerChange(updatedPlayer)
                    })
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        val updatedPlayer = player.copy(number = number.toIntOrNull() ?: player.number, name = name)
                        onPlayerChange(updatedPlayer)
                    })
                )
            }
        }
    )
}
