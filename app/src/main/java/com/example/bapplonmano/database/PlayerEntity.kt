package com.example.bapplonmano

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: Int,
    val name: String,
    val isGoalkeeper: Boolean
)
