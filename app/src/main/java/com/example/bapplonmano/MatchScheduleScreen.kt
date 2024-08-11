// MatchScheduleScreen.kt
package com.example.bapplonmano

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bapplonmano.model.Match
import com.example.bapplonmano.model.sampleMatches

@Composable
fun MatchScheduleScreen(navController: NavHostController) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(sampleMatches) { match ->
            MatchCard(match)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchCard(match: Match) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${match.team1} vs ${match.team2}")
            Text(text = "Date: ${match.date}")
        }
    }
}
