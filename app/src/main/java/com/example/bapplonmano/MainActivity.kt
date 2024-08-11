// MainActivity.kt
package com.example.bapplonmano

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bapplonmano.ui.theme.BapplonmanoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BapplonmanoTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("team") { TeamScreen(navController) }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BapplonmanoTheme {
        HomeScreen(rememberNavController())
    }
}
