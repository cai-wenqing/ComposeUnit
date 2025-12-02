package com.zcrain.composeunit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.zcrain.composeunit.NaviConfig
import com.zcrain.composeunit.ui.components.BigPosterScreen
import com.zcrain.composeunit.ui.components.CodeInputScreen
import com.zcrain.composeunit.ui.components.HomeScreen
import com.zcrain.composeunit.ui.components.LoadingScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding.calculateTopPadding())
                ) {
                    val backStack =
                        rememberSaveable { mutableStateListOf(NaviConfig.ROUTE_HOME) }
                    NavDisplay(
                        backStack = backStack,
                        onBack = {
                            backStack.removeLastOrNull() ?: finish()
                        },
                        entryProvider = { entry ->
                            when (entry) {
                                NaviConfig.ROUTE_HOME -> NavEntry(entry) {
                                    HomeScreen(backStack)
                                }

                                NaviConfig.ROUTE_LOADING -> NavEntry(entry) {
                                    LoadingScreen()
                                }

                                NaviConfig.ROUTE_CODE_INPUT -> NavEntry(entry) {
                                    CodeInputScreen()
                                }

                                NaviConfig.ROUTE_BIG_POSTER -> NavEntry(entry) {
                                    BigPosterScreen()
                                }

                                else -> NavEntry(entry) {
                                    HomeScreen(backStack)
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}