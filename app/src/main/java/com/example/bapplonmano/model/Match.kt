// Match.kt
package com.example.bapplonmano.model

data class Match(val team1: String, val team2: String, val date: String)

val sampleMatches = listOf(
    Match("Team A", "Team B", "2024-08-10"),
    Match("Team C", "Team D", "2024-08-11"),
    // Add more matches here
)
