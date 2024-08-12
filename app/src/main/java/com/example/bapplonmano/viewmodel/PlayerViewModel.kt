package com.example.bapplonmano

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val playerDao = AppDatabase.getDatabase(application).playerDao()
    private val _players = MutableStateFlow<List<PlayerEntity>>(emptyList())
    val players: StateFlow<List<PlayerEntity>> = _players.asStateFlow()

    init {
        viewModelScope.launch {
            val playersFromDb = playerDao.getAllPlayers()
            if (playersFromDb.isEmpty()) {
                // Initialize with default players if database is empty
                val defaultPlayers = (1..16).map { PlayerEntity(number = it, name = "Player $it", isGoalkeeper = false) } +
                        (17..19).map { PlayerEntity(number = it, name = "Goalkeeper ${it - 16}", isGoalkeeper = true) }
                playerDao.insertPlayers(defaultPlayers)
                _players.value = playerDao.getAllPlayers()  // Reload players to get generated IDs
                Log.d("PlayerViewModel", "Initialized with default players: ${_players.value}")
            } else {
                _players.value = playersFromDb
                Log.d("PlayerViewModel", "Fetched players: ${_players.value}")
            }
        }
    }

    fun updatePlayer(player: PlayerEntity) {
        viewModelScope.launch {
            playerDao.updatePlayer(player)
            _players.value = playerDao.getAllPlayers()
            Log.d("PlayerViewModel", "Player updated: $player. All players: ${_players.value}")
        }
    }

    fun deleteAllPlayers() {
        viewModelScope.launch {
            playerDao.deleteAllPlayers()
            _players.value = playerDao.getAllPlayers()
            Log.d("PlayerViewModel", "All players deleted. Remaining players: ${_players.value}")
        }
    }
}
